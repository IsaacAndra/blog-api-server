package com.isaacandrade.blog.service;

import com.isaacandrade.blog.domain.projects.Project;
import com.isaacandrade.blog.domain.technologies.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TechnologyService {

    @Autowired
    TechnologyRepository technologyRepository;


    public TechnologyDto createTechnology(CreateTechnologyDto data) {
        CreateTechnologyDto newTechnology = new CreateTechnologyDto(data.name(), data.type());

        Technology technology = new Technology(newTechnology);
        Technology savedTechnology = technologyRepository.save(technology);

        return mapToTechnologyDTO((List<Technology>) savedTechnology);
    }

    public TechnologyDto getAllTechnologies() {
        List<Technology> technology = technologyRepository.findAll();

        return mapToTechnologyDTO(technology);
    }

    public TechnologyDto getTechnologyByType(TechnologyType type) {
        List<Technology> technologies = technologyRepository.findByType(type);

        return mapToTechnologyDTO(technologies);
    }

    public Optional<Technology> updateTechnology(Long id, Technology updatedTechnology) {
        return technologyRepository.findById(id).map(existingTechnology -> {
            existingTechnology.setName(updatedTechnology.getName());
            existingTechnology.setType(updatedTechnology.getType());
            return technologyRepository.save(existingTechnology);

        });
    }

    public void deleteTechnology(Long id) {
        technologyRepository.deleteById(id);
    }

    private TechnologyDto mapToTechnologyDTO(List<Technology> technologies) {
        // Separando tecnologias por tipo
        List<String> frontend = technologies.stream()
                .filter(tech -> tech.getType() == TechnologyType.FRONTEND)
                .map(Technology::getName)
                .collect(Collectors.toList());

        List<String> backend = technologies.stream()
                .filter(tech -> tech.getType() == TechnologyType.BACKEND)
                .map(Technology::getName)
                .collect(Collectors.toList());

        // Retornando um DTO agrupado
        return new TechnologyDto(frontend, backend);
    }
}
