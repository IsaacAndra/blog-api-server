package com.isaacandrade.blog.controller;

import com.isaacandrade.blog.domain.projects.CreateProjectDto;
import com.isaacandrade.blog.domain.projects.ProjectDto;
import com.isaacandrade.blog.domain.user.UserDTO;
import com.isaacandrade.blog.service.ProjectService;
import com.isaacandrade.blog.utils.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@Tag(name = "Projetos" , description = "Endpoints para Gerenciar os Projetos")
public class ProjectController {

    @Autowired
    ProjectService projectService;


    @GetMapping(
            produces = {MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML
            }
    )
    @Operation(
            summary = "Encontre todos os Projetos",
            description = "Encontre todos os Projetos",
            tags = {"Projetos"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProjectDto.class))
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unautorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<ProjectDto>> getAllProjects(){
        List<ProjectDto> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }


    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Operation(summary = "Encontre o Projeto pelo Id", description = "Encontre o Usuário pelo Id",
            tags = {"Projetos"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProjectDto.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unautorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        ProjectDto projectById = projectService.getProjectById(id);

        return ResponseEntity.ok(projectById);
    }


    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Transactional
    @Operation(
            summary = "Cria um novo projeto",
            description = "Cria um novo projeto passando uma representação em JSON, XML ou YML ",
            tags = {"Projetos"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProjectDto.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<ProjectDto> createProject(@RequestBody CreateProjectDto data) {
        ProjectDto creatingProject = projectService.createProject(data);
        return new ResponseEntity<>(creatingProject, HttpStatus.CREATED);
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Transactional
    @Operation(
            summary = "Atualiza os dados de um projeto",
            description = "Atualiza os dados de um projeto passando uma representação em JSON, XML ou YML ",
            tags = {"Projetos"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProjectDto.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<Optional<ProjectDto>> updateProject(@PathVariable Long id, @RequestBody CreateProjectDto data) {
        Optional<ProjectDto> updatingProject = projectService.updateProject(id, data);

        return ResponseEntity.ok(updatingProject);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleta um projeto",
            description = "Deleta um projeto passando uma representação em JSON, XML ou YML ",
            tags = {"Projetos"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<ProjectDto> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);

        return ResponseEntity.noContent().build();
    }
}
