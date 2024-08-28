package com.isaacandrade.blog.unittests;

import com.isaacandrade.blog.domain.post.*;
import com.isaacandrade.blog.domain.user.User;
import com.isaacandrade.blog.domain.user.UserDTO;
import com.isaacandrade.blog.exception.ConstraintViolationException;
import com.isaacandrade.blog.exception.PostNotFoundException;
import com.isaacandrade.blog.service.PostService;
import com.isaacandrade.blog.service.UserService;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class PostServiceTest {
    @InjectMocks
    PostService postService;

    @Mock
    UserService userService;

    @Mock
    PostRepository postRepository;

    User author;
    UserDTO authorDTO;
    Post post;
    List<Post> posts;
    PostDTO postDTO;
    CreatePostDTO createPostDTO;
    EditPostDTO editPostDTO;
    AuthorWithPostsDTO expectedDTO;

    //------------Setup the MOCKS----------------------------------------------------------------------
    @BeforeEach
    public void setUp(){
        author = new User(1L, "Felipe", "teste@gmail.com","admin123");
        authorDTO = new UserDTO(1L, "teste@gmail.com", "Felipe");

        Post post1 = new Post(1L, "Title 1", "Content 1", LocalDateTime.now(), new User(), true);
        Post post2 = new Post(2L, "Title 2", "Content 2", LocalDateTime.now(), new User(), false);
        posts = List.of(post1, post2);

        List<PostDTO> postDTOS = posts.stream()
                .filter(Post::getIsActive)
                .map(post -> new PostDTO(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt(), post.getIsActive()))
                .collect(Collectors.toList());
        expectedDTO = new AuthorWithPostsDTO(authorDTO, postDTOS);

        post = new Post(1L, "Titulo", "content", LocalDateTime.now(), author, true);
        postDTO = new PostDTO(1L, "Titulo", "content", LocalDateTime.now(), true);
        createPostDTO = new CreatePostDTO("Titulo", "content", true);
        editPostDTO = new EditPostDTO("Titulo", "content");
    }


    //----------TestsFindActivesPosts----------------------------------------------------------
    @Test
    void findAllActivesPostsWithSuccess(){
        when(postRepository.findByIsActiveTrue()).thenReturn(Collections.singletonList(post));

        List<PostDTO> result = postService.findAllActives();

        assertNotNull(result);
        assertEquals(1, result.size());

        //Aqui estou arredondando os milisegundos do createdAt para que passem no test :p
        LocalDateTime expectedDate = postDTO.createdAt().truncatedTo(ChronoUnit.MILLIS);
        LocalDateTime actualDate = result.get(0).createdAt().truncatedTo(ChronoUnit.MILLIS);

        assertTrue(expectedDate.isEqual(actualDate), "Expected date: " + expectedDate + ", but was: " + actualDate);

        assertEquals(postDTO, result.get(0));

        verify(postRepository).findByIsActiveTrue();
        verifyNoMoreInteractions(postRepository);
    }
    @Test
    void findAllActivesWhenActivePostsShouldException(){
        when(postRepository.findByIsActiveTrue()).thenReturn(Collections.emptyList());

        PostNotFoundException exception = assertThrows(PostNotFoundException.class, () -> {
            postService.findAllActives();
        });

        MatcherAssert.assertThat(exception, notNullValue());

        verify(postRepository).findByIsActiveTrue();
        verifyNoMoreInteractions(postRepository);
    }


    //----------TestsFindPostsWithAuthorId--------------------------------------
    @Test
    void findPostsWithAuthorIdWhenAuthorAndPostsExist(){
        when(userService.findUserById(1L)).thenReturn(authorDTO);
        when(postRepository.findPostsByAuthorId(1L)).thenReturn(posts);

        AuthorWithPostsDTO resutl = postService.findPostsWithAuthorId(1L);

        assertNotNull(resutl);
        assertEquals(expectedDTO, resutl);


        verify(userService).findUserById(1L);
        verify(postRepository).findPostsByAuthorId(1L);
        verifyNoMoreInteractions(userService, postRepository);
    }

    @Test
    void findPostsWithAuthorIdWhenNoActivePosts(){
        when(userService.findUserById(1L)).thenReturn(authorDTO);
        when(postRepository.findPostsByAuthorId(1L))
                .thenReturn(
                        Collections.singletonList(
                                new Post(
                                        3L,
                                        "Title 3",
                                        "Content 3",
                                        LocalDateTime.now(),
                                        new User(),
                                        false
                                )
                        )
                );

        AuthorWithPostsDTO result = postService.findPostsWithAuthorId(1L);

        assertNotNull(result);
        assertEquals(new AuthorWithPostsDTO(authorDTO, Collections.emptyList()), result);

        verify(userService).findUserById(1L);
        verify(postRepository).findPostsByAuthorId(1L);
        verifyNoMoreInteractions(userService, postRepository);
    }


    //----------TestsFindPostsById------------------------------------------------
    @Test
    void findPostsByIdWithSuccess(){
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        PostDTO result = postService.findById(post.getId());

        assertNotNull(result);
        assertEquals(post.getTitle(), result.title());
        assertEquals(post.getContent(), result.content());
        assertEquals(post.getCreatedAt(), result.createdAt());
        assertEquals(post.getIsActive(), result.isActive());

        verify(postRepository).findById(post.getId());
        verifyNoMoreInteractions(postRepository);
    }

    @Test
    void findPostsByIdWhenPostWasNoFoundShouldException(){
        when(postRepository.findById(post.getId())).thenReturn(Optional.empty());

        PostNotFoundException exception = assertThrows(PostNotFoundException.class, () -> {
            postService.findById(post.getId());
        });

        MatcherAssert.assertThat(exception, notNullValue());
        MatcherAssert.assertThat(exception.getMessage(), is("Post With Id " + post.getId() + " Was Not Found"));

        verify(postRepository).findById(post.getId());
        verifyNoMoreInteractions(postRepository);
    }


    //----------CreatePostTests---------------------------------------------
    @Test
    void createPostWithSuccess(){
        when(userService.findUserById(anyLong())).thenReturn(authorDTO);
        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostDTO result = postService.createPost(createPostDTO, authorDTO.id());

        assertNotNull(result);
        assertEquals(postDTO.title(), result.title());
        assertEquals(postDTO.content(), result.content());
        assertEquals(postDTO.isActive(), result.isActive());

        verify(postRepository).save(any(Post.class));
        verify(userService).findUserById(authorDTO.id());
        verifyNoMoreInteractions(postRepository);
    }

    @Test
    void createPostWhithNullValuesShouldException(){
        when(userService.findUserById(anyLong())).thenReturn(authorDTO);
        CreatePostDTO invalidDTo = new CreatePostDTO(null,null,null);

        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
            postService.createPost(invalidDTo, authorDTO.id());
        });

        MatcherAssert.assertThat(exception, notNullValue());
        MatcherAssert.assertThat(exception.getMessage(), is("title and content cannot be null!"));

        verify(postRepository, never()).save(any(Post.class));
    }


    //----------UpdatePostTests---------------------------------------------------------------
    @Test
    void updatePostsWithSuccess(){
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        PostDTO result = postService.updatePost(post.getId(), editPostDTO);

        assertNotNull(result);
        assertEquals(editPostDTO.title(), result.title());
        assertEquals(editPostDTO.content(), result.content());

        verify(postRepository).findById(post.getId());
        verify(postRepository).save(post);
        verifyNoMoreInteractions(postRepository);
    }
    @Test
    void updatePostWhenPostWasNotFoundShouldException(){
        when(postRepository.findById(post.getId())).thenReturn(Optional.empty());

        PostNotFoundException exception = assertThrows(PostNotFoundException.class, () -> {
            postService.updatePost(post.getId(), editPostDTO);
        });

        MatcherAssert.assertThat(exception, notNullValue());
        MatcherAssert.assertThat(exception.getMessage(), is("Post With Id " + post.getId() + " Was Not Found"));

        verify(postRepository).findById(post.getId());
        verifyNoMoreInteractions(postRepository);
    }


    //----------DeletePostByIdTests---------------------------------------------------------
    @Test
    void deletePostByIdWithSuccess(){
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        postService.deletePost(post.getId());

        verify(postRepository).findById(post.getId());
        verify(postRepository).save(post);
        verifyNoMoreInteractions(postRepository);
    }
    @Test
    void deletePostWhenPostWasNotFoundShouldException(){
        when(postRepository.findById(post.getId())).thenReturn(Optional.empty());

        PostNotFoundException exception = assertThrows(PostNotFoundException.class, () -> {
            postService.deletePost(post.getId());
        });

        MatcherAssert.assertThat(exception, notNullValue());
        MatcherAssert.assertThat(
                exception.getMessage(),
                is("Post With Id " + post.getId() + " Was Not Found")
        );

        verify(postRepository).findById(post.getId());
        verifyNoMoreInteractions(postRepository);
    }

}
