package com.isaacandrade.blog.controller;

import com.isaacandrade.blog.domain.about.AboutDTO;
import com.isaacandrade.blog.domain.about.CreateAboutDTO;
import com.isaacandrade.blog.domain.about.EditAboutDTO;
import com.isaacandrade.blog.exception.AboutNotFoundException;
import com.isaacandrade.blog.service.AboutService;
import com.isaacandrade.blog.utils.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/about")
@Tag(name = "About", description = "Endpoints para Gerenciar o About")
public class AboutController {

    @Autowired
    AboutService aboutService;



    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Operation(summary = "Encontre o About pelo Titulo", description = "Encontre o About pelo Titulo",
            tags = {"About"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = AboutDTO.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<AboutDTO> findAboutByTitle(@PathVariable Long id) throws AboutNotFoundException {
        AboutDTO postsByTitle = aboutService.findContentById(id);
        return ResponseEntity.ok(postsByTitle);
    }



    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Transactional
    @Operation(
            summary = "Cria um novo about",
            description = "Cria um novo about passando uma representação em JSON, XML ou YML",
            tags = {"About"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = AboutDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<AboutDTO> createPost(@RequestBody CreateAboutDTO data){
        AboutDTO creatingAbout = aboutService.createContent(data);
        return new ResponseEntity<>(creatingAbout, HttpStatus.CREATED);
    }



    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Transactional
    @Operation(
            summary = "Atualiza os dados do about",
            description = "Atualiza os dados do about passando uma representação em JSON, XML ou YML ",
            tags = {"About"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = AboutDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<AboutDTO> updatePost(@PathVariable Long id, @RequestBody EditAboutDTO data) throws AboutNotFoundException {
        AboutDTO editedAbout = aboutService.updateContent(id, data);
        return ResponseEntity.ok(editedAbout);
    }



    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleta o About",
            description = "Deleta o about passando uma representação em JSON, XML ou YML ",
            tags = {"About"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity deletePost(@PathVariable Long id) throws AboutNotFoundException {
        aboutService.deleteAbout(id);
        return ResponseEntity.noContent().build();
    }
}

