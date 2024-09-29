package com.isaacandrade.blog.controller;

import com.isaacandrade.blog.domain.post.AuthorWithPostsDTO;
import com.isaacandrade.blog.domain.post.CreatePostDTO;
import com.isaacandrade.blog.domain.post.EditPostDTO;
import com.isaacandrade.blog.domain.post.PostDTO;
import com.isaacandrade.blog.domain.user.UserDTO;
import com.isaacandrade.blog.service.PostService;
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

@RestController
@RequestMapping("/posts")
@Tag(name = "Posts", description = "Endpoints para Gerenciar os Posts")
public class PostController {
    @Autowired
    PostService postService;



    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Encontre todos os Posts Ativos", description = "Encontre todos os Posts Ativos",
            tags = {"Posts"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            array = @ArraySchema(schema = @Schema(implementation = PostDTO.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<PostDTO>> findAllPosts(){
        List<PostDTO> posts = postService.findAllActives();
        return ResponseEntity.ok(posts);
    }



    @GetMapping(
            value = "/author/{authorId}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Operation(
            summary = "Encontre todos os Posts Ativos com o Author do mesmo",
            description = "Encontre todos os Posts Ativos com o Author do mesmo",
            tags = {"Posts"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = AuthorWithPostsDTO.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<AuthorWithPostsDTO> getPostsByAuthor(@PathVariable Long authorId){
        AuthorWithPostsDTO postsByAuthor = postService.findPostsWithAuthorId(authorId);
        return ResponseEntity.ok(postsByAuthor);
    }



    @GetMapping(
            value = "/{title}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Operation(summary = "Encontre o Post pelo Titulo", description = "Encontre o Post pelo Titulo",
            tags = {"Posts"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PostDTO.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<PostDTO> findPostByTitle(@PathVariable String title){
        PostDTO postsByTitle = postService.findByTitle(title);
        return ResponseEntity.ok(postsByTitle);
    }



    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Transactional
    @Operation(
            summary = "Cria um novo post",
            description = "Cria um novo post passando uma representação em JSON, XML ou YML",
            tags = {"Posts"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PostDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<PostDTO> createPost(@RequestBody CreatePostDTO data, @RequestParam Long authorId){
        PostDTO creatingPost = postService.createPost(data, authorId);
        return new ResponseEntity<>(creatingPost, HttpStatus.CREATED);
    }



    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Transactional
    @Operation(
            summary = "Atualiza os dados de um post",
            description = "Atualiza os dados de um post passando uma representação em JSON, XML ou YML ",
            tags = {"Posts"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PostDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody EditPostDTO data){
        PostDTO editedPost = postService.updatePost(id, data);
        return ResponseEntity.ok(editedPost);
    }



    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleta um post",
            description = "Deleta um post passando uma representação em JSON, XML ou YML ",
            tags = {"Posts"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}