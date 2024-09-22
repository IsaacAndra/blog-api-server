package com.isaacandrade.blog.controller;

import com.isaacandrade.blog.domain.technologies.CreateTechnologyDto;
import com.isaacandrade.blog.domain.technologies.Technology;
import com.isaacandrade.blog.domain.technologies.TechnologyDto;
import com.isaacandrade.blog.domain.technologies.TechnologyType;
import com.isaacandrade.blog.service.TechnologyService;
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
@RequestMapping("/technologies")
@Tag(name = "Tecnologias" , description = "Endpoints para Gerenciar os Tecnologias")
public class TechnologyController {

    @Autowired
    private TechnologyService technologyService;

    // Endpoint para criar uma nova tecnologia
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Transactional
    @Operation(
            summary = "Cria uma nova Tecnologia",
            description = "Cria uma nova tecnologia passando uma representação em JSON, XML ou YML ",
            tags = {"Tecnologias"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Technology.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<TechnologyDto> createTechnology(@RequestBody CreateTechnologyDto data) {
        TechnologyDto createdTechnology = technologyService.createTechnology(data);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTechnology);
    }

    // Endpoint para obter uma tecnologia pelo ID
    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Operation(summary = "Encontre a Tecnologia pelo Id", description = "Encontre a Tecnologia pelo Id",
            tags = {"Tecnologias"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Technology.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unautorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<Technology> getTechnologyById(@PathVariable Long id) {
        Optional<Technology> technology = technologyService.getTechnologyById(id);
        return technology.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Endpoint para listar todas as tecnologias
    @GetMapping(
            produces = {MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            MediaType.APPLICATION_YML
    })
    @Operation(
            summary = "Encontre todas as Tecnologias",
            description = "Encontre todas as Tecnologias",
            tags = {"Tecnologias"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Technology.class))
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unautorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<Technology>> getAllTechnologies() {
        List<Technology> technologies = technologyService.getAllTechnologies();
        return ResponseEntity.ok(technologies);
    }

    // Endpoint para obter tecnologias por tipo
    @GetMapping("/type/{type}")
    @Operation(summary = "Encontre a Tecnologia pelo tipo", description = "Encontre a Tecnologia pelo tipo",
            tags = {"Tecnologias"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Technology.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unautorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<Technology>> getTechnologyByType(@PathVariable String type) {
        TechnologyType technologyType = TechnologyType.valueOf(type.toUpperCase());
        List<Technology> technologies = technologyService.getTechnologyByType(technologyType);
        return ResponseEntity.ok(technologies);
    }

    // Endpoint para atualizar uma tecnologia
    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Transactional
    @Operation(
            summary = "Atualiza os dados de uma tecnologia",
            description = "Atualiza os dados de uma tecnologia passando uma representação em JSON, XML ou YML ",
            tags = {"Tecnologias"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Technology.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<Technology> updateTechnology(@PathVariable Long id, @RequestBody Technology updatedTechnology) {
        Optional<Technology> technology = technologyService.updateTechnology(id, updatedTechnology);
        return technology.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Endpoint para deletar uma tecnologia
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleta uma tecnologia",
            description = "Deleta uma tecnologia passando uma representação em JSON, XML ou YML ",
            tags = {"Tecnologias"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<Void> deleteTechnology(@PathVariable Long id) {
        technologyService.deleteTechnology(id);
        return ResponseEntity.noContent().build();
    }
}