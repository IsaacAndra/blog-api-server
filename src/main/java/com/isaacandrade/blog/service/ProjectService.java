package com.isaacandrade.blog.service;

import com.isaacandrade.blog.domain.projects.CreateProjectDto;
import com.isaacandrade.blog.domain.projects.Project;
import com.isaacandrade.blog.domain.projects.ProjectDto;
import com.isaacandrade.blog.domain.projects.ProjectRepository;
import com.isaacandrade.blog.domain.technologies.Technology;
import com.isaacandrade.blog.domain.technologies.TechnologyDto;
import com.isaacandrade.blog.domain.technologies.TechnologyRepository;
import com.isaacandrade.blog.domain.technologies.TechnologyType;
import com.isaacandrade.blog.exception.ConstraintViolationException;
import com.isaacandrade.blog.exception.ProjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TechnologyRepository technologyRepository;

    // Metodo que para listar todos os projetos
    public List<ProjectDto> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        if (projects.isEmpty()){
            throw new ProjectNotFoundException();
        }
        return projects.stream().map(this::mapToProjectDTO).collect(Collectors.toList());
    }

    // Metodo para mostrar um projeto pelo id
    public ProjectDto getProjectById(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new ProjectNotFoundException("Project With id " + id + " Was Not Found!")
        );

        return mapToProjectDTO(project);
    }

    public ProjectDto createProject(CreateProjectDto data) {
        // 1. Criar e salvar o projeto sem as tecnologias
        Project project = new Project();
        project.setName(data.name());
        project.setDescription(data.description());
        project.setUrl(data.url());
        project.setUrlGitHub(data.urlGitHub());

        if (data.name() == null || data.description() == null || data.technologies() == null || data.url() == null || data.urlGitHub() == null) {
            throw new ConstraintViolationException("Name, Description, Technologies, Url and GitHubUrl Cannot Be Null!");
        }

        project = projectRepository.save(project);

        // 2. Associa as tecnologias ao projeto usando os nomes e tipos
        Project finalProject = project;
        Set<Technology> frontendTechnologies = data.technologies().frontend().stream()
                .map(techName -> getOrCreateTechnology(techName, TechnologyType.FRONTEND, finalProject))
                .collect(Collectors.toSet());

        Project finalProject1 = project;
        Set<Technology> backendTechnologies = data.technologies().backend().stream()
                .map(techName -> getOrCreateTechnology(techName, TechnologyType.BACKEND, finalProject1))
                .collect(Collectors.toSet());

        // 3. Salvar todas as tecnologias (novas ou atualizadas)
        Set<Technology> allTechnologies = new HashSet<>(frontendTechnologies);
        allTechnologies.addAll(backendTechnologies);
        technologyRepository.saveAll(allTechnologies);

        // 4. Atualizar o projeto com as tecnologias
        project.setTechnologies(new ArrayList<>(allTechnologies));

        // 5. Retornar o DTO atualizado do projeto
        return mapToProjectDTO(projectRepository.save(project));
    }

    public Optional<ProjectDto> updateProject(Long id, CreateProjectDto data) {

        projectRepository.findById(id).orElseThrow(() ->
                new ProjectNotFoundException("Project With id " + id + " Was Not Found!"));

        return projectRepository.findById(id).map(existingProject -> {
            // Atualiza as informações básicas do projeto
            existingProject.setName(data.name());
            existingProject.setDescription(data.description());
            existingProject.setUrl(data.url());
            existingProject.setUrlGitHub(data.urlGitHub());


            // Remove as tecnologias antigas
            List<Technology> existingTechnologies = existingProject.getTechnologies();
            technologyRepository.deleteAll(existingTechnologies);

            // Cria novas tecnologias com base no DTO
            List<Technology> newTechnologies = new ArrayList<>();

            // Adiciona tecnologias de frontend
            for (String techName : data.technologies().frontend()) {
                newTechnologies.add(convertTechnologyDTOToEntity(techName, TechnologyType.FRONTEND, existingProject));
            }

            // Adiciona tecnologias de backend
            for (String techName : data.technologies().backend()) {
                newTechnologies.add(convertTechnologyDTOToEntity(techName, TechnologyType.BACKEND, existingProject));
            }

            // Salva as novas tecnologias
            technologyRepository.saveAll(newTechnologies);

            // Atualiza o projeto com as novas tecnologias
            existingProject.setTechnologies(newTechnologies);
            Project updatedProject = projectRepository.save(existingProject);

            // Retorna o DTO atualizado
            return mapToProjectDTO(updatedProject);
        });
    }

    public void deleteProject(Long id) {

        projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException("Project With id " + id + " Was Not Found!"));

        projectRepository.findById(id).ifPresent(project -> {
            technologyRepository.deleteAll(project.getTechnologies()); // Remove todas as tecnologias associadas
            projectRepository.delete(project); // Remove o projeto
        });
    }






    private ProjectDto mapToProjectDTO(Project project) {
        // Agrupa as tecnologias em frontend e backend
        TechnologyDto technologyGroupDto = new TechnologyDto(
                project.getTechnologies().stream()
                        .filter(tech -> tech.getType() == TechnologyType.FRONTEND)
                        .map(Technology::getName)
                        .collect(Collectors.toList()),
                project.getTechnologies().stream()
                        .filter(tech -> tech.getType() == TechnologyType.BACKEND)
                        .map(Technology::getName)
                        .collect(Collectors.toList())
        );
        return new ProjectDto(
                project.getId(),
                project.getName(),
                project.getDescription(),
                technologyGroupDto, // Usa o DTO agrupado
                project.getUrl(),
                project.getUrlGitHub()
        );
    }



    private Technology convertTechnologyDTOToEntity(String name, TechnologyType type, Project project) {
        Technology technology = new Technology();
        technology.setName(name);
        technology.setType(type);
        technology.setProject(project);
        return technology;
    }



    private Technology getOrCreateTechnology(String name, TechnologyType type, Project project) {
        Technology technology = technologyRepository.findByNameAndType(name, type);
        if (technology == null) {
            technology = new Technology();
            technology.setName(name);
            technology.setType(type);
            technology.setProject(project);
        } else {
            technology.setProject(project);
        }
        return technology;
    }
}
