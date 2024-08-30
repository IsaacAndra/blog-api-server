package com.isaacandrade.blog.integrationtests.post;

import com.isaacandrade.blog.domain.post.*;
import com.isaacandrade.blog.domain.user.CreateUserDTO;
import com.isaacandrade.blog.domain.user.UserDTO;
import com.isaacandrade.blog.integrationtests.config.MyIntegrationTest;
import com.isaacandrade.blog.service.PostService;
import com.isaacandrade.blog.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
public class PostIntegrationTest extends MyIntegrationTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Test
    void findAllPostsWithsucces() {
        //Aqui estou criando um usuário para que seja o author dos posts
        CreateUserDTO newUser = new CreateUserDTO("Isaac", "isaactest@gmail.com", "isaactest123");
        UserDTO userFounded = userService.createUser(newUser);
        Long userId = userFounded.id();

        //Verificando se o usuário foi criado
        assertNotNull(newUser);
        //Verificandoo se o usuário foi encontrado
        assertNotNull(userFounded);


        //Aqui estou criando os posts que serão relacionados ao id do author (userId)
        CreatePostDTO post1 = new CreatePostDTO("Titulo1", "Conteúdo do post1", null);
            postService.createPost(post1, userId);
        CreatePostDTO post2 = new CreatePostDTO("Titulo2", "Conteúdo do post2", null);
            postService.createPost(post2, userId);

        //Verificando se os 2posts foram criados
        assertNotNull(post1);
        assertNotNull(post2);

        List<PostDTO> posts =  postService.findAllActives();

        //Verificando se os posts foram encontrados
        assertNotNull(posts);
        assertEquals(2, posts.size());

        PostDTO postFounded1 = posts.get(0);
        assertEquals("Titulo1", postFounded1.title());
        assertEquals("Conteúdo do post1", postFounded1.content());
        assertEquals(true, postFounded1.isActive());

        PostDTO postFounded2 = posts.get(1);
        assertEquals("Titulo2", postFounded2.title());
        assertEquals("Conteúdo do post2", postFounded2.content());
        assertEquals(true, postFounded2.isActive());
    }

    @Test
    void findPostsWithAuthorIdWithSuccess(){
        //Aqui estou criando um usuário para que seja o author do post
        CreateUserDTO newUser = new CreateUserDTO("Isaac", "isaactest@gmail.com", "isaactest123");
        UserDTO userCreated = userService.createUser(newUser);
        Long userId = userCreated.id();

        //Verificando se o usuário foi criado
        assertNotNull(newUser);


        //Verificando se o usuário foi encontrado
        UserDTO userFounded = userService.findUserById(userId);
        assertNotNull(userFounded);


        //Aqui estou criando os posts que serão relacionados ao id do author (userId)
        CreatePostDTO post1 = new CreatePostDTO("Titulo1", "Conteúdo do post1", null);
            postService.createPost(post1, userId);
        CreatePostDTO post2 = new CreatePostDTO("Titulo2", "Conteúdo do post2", null);
            postService.createPost(post2, userId);

        //Verificando se os 2 posts foram criados
        assertNotNull(post1);
        assertNotNull(post2);


        //Verificando se a lista de posts foi encontrada
        List<PostDTO> posts =  postService.findAllActives();

        assertNotNull(posts);
        assertEquals(2, posts.size());

        PostDTO postFounded1 = posts.get(0);
        assertEquals("Titulo1", postFounded1.title());
        assertEquals("Conteúdo do post1", postFounded1.content());
        assertEquals(true, postFounded1.isActive());

        PostDTO postFounded2 = posts.get(1);
        assertEquals("Titulo2", postFounded2.title());
        assertEquals("Conteúdo do post2", postFounded2.content());
        assertEquals(true, postFounded2.isActive());

        //Aqui estarei encontrando a lista de posts com seu respectivo autor
        AuthorWithPostsDTO authorWithPosts = postService.findPostsWithAuthorId(userId);

        assertNotNull(authorWithPosts);
        assertEquals(userFounded, authorWithPosts.author());
        assertEquals(posts, authorWithPosts.posts());
    }

    @Test
    void createAPostWithSuccess(){
        //Aqui estou criando um usuário para que seja o author dos posts
        CreateUserDTO newUser = new CreateUserDTO("Isaac", "isaactest@gmail.com", "isaactest123");
        UserDTO userFounded = userService.createUser(newUser);
        Long userId = userFounded.id();

        //Verificando se o usuário foi criado
        assertNotNull(newUser);
        //Verificandoo se o usuário foi encontrado
        assertNotNull(userFounded);


        //Aqui estou criando os posts que serão relacionados ao id do author (userId)
        CreatePostDTO post = new CreatePostDTO("Titulo1", "Conteúdo do post1", true);
        postService.createPost(post, userId);

        //Verificando se o post foi criado
        assertNotNull(post);
        assertNotNull(userFounded.id());
        assertTrue(post.isActive());
    }

    @Test
    void updatePostWithSuccess(){
        //Aqui estou criando um usuário para que seja o author dos posts
        CreateUserDTO newUser = new CreateUserDTO("Isaac", "isaactest@gmail.com", "isaactest123");
        UserDTO userFounded = userService.createUser(newUser);
        Long userId = userFounded.id();

        //Verificando se o usuário foi criado
        assertNotNull(newUser);
        //Verificandoo se o usuário foi encontrado
        assertNotNull(userFounded);


        //Aqui estou criando os posts que serão relacionados ao id do author (userId)
        CreatePostDTO post = new CreatePostDTO("Titulo1", "Conteúdo do post1", true);
        PostDTO createdPost = postService.createPost(post, userId);

        //Verificando se o post foi criado
        assertNotNull(createdPost);
        assertNotNull(userFounded.id());
        assertTrue(post.isActive());

        EditPostDTO postEdit = new EditPostDTO("TituloNovo", "Conteudo novo");
        PostDTO postEdited = postService.updatePost(userId, postEdit);

        //Varifica se o post foi editado
        assertNotNull(postEdited);
        assertEquals("TituloNovo", postEdited.title());
        assertEquals("Conteudo novo", postEdited.content());
    }

    @Test
    void deletePostWithSuccess(){
        //Aqui estou criando um usuário para que seja o author dos posts
        CreateUserDTO newUser = new CreateUserDTO("Isaac", "isaactest@gmail.com", "isaactest123");
        UserDTO userFounded = userService.createUser(newUser);
        Long userId = userFounded.id();

        //Verificando se o usuário foi criado
        assertNotNull(newUser);
        //Verificandoo se o usuário foi encontrado
        assertNotNull(userFounded);


        //Aqui estou criando os posts que serão relacionados ao id do author (userId)
        CreatePostDTO post = new CreatePostDTO("Titulo1", "Conteúdo do post1", true);
        PostDTO createdPost = postService.createPost(post, userId);

        //Verificando se o post foi criado
        assertNotNull(createdPost);
        assertNotNull(userFounded.id());
        assertTrue(post.isActive());

        postService.deletePost(createdPost.id());

        PostDTO postDeleted = postService.findById(createdPost.id());

        assertFalse(postDeleted.isActive(), "Deve estar com isActive setado como Falso!");
        assertNotNull(createdPost);
    }
}
