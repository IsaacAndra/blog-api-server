package com.isaacandrade.blog.unittests;

import com.isaacandrade.blog.domain.user.*;
import com.isaacandrade.blog.exception.ConstraintViolationException;
import com.isaacandrade.blog.exception.UserNotFoundException;
import com.isaacandrade.blog.service.UserService;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceUnitTest {
    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    User user;
    UserDTO userDTO;
    CreateUserDTO createUserDTO;
    EditUserDTO editUserDTO;

    //------------Setup the MOCKS----------------------------------------------------------------------
    @BeforeEach
    public void setUp(){
        user = new User(0L ,"Felipe", "teste@gmail.com", "qualquer", UserRole.ADMIN);
        userDTO = new UserDTO(0L, "teste@gmail.com", "Felipe", UserRole.ADMIN);
        createUserDTO = new CreateUserDTO("Felipe", "teste@gmail.com", "admin123", UserRole.ADMIN);
        editUserDTO = new EditUserDTO("Felipe", "teste@gmail.com", "admin123", UserRole.ADMIN);
    }


    //----------TestsFindAllUsers-----------
    @Test
    void findAllUserWithSuccess(){
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        List<UserDTO> result = userService.findAllUsers();

        assertEquals(Collections.singletonList(userDTO), result);
        verify(userRepository).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void throwAErrorMessageWhenUsersWasNotFound(){
       when(userRepository.findAll()).thenReturn(Collections.emptyList());

       UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
           userService.findAllUsers();
       });

        MatcherAssert.assertThat(exception, notNullValue());
        MatcherAssert.assertThat(exception.getMessage(), is("User Was Not Found!"));

        verify(userRepository).findAll();
        verifyNoMoreInteractions(userRepository);
    }


    //----------TestsFindUserById-----------
    @Test
    void findUserByIdWithSuccess(){
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserDTO result = userService.findUserById(user.getId());

        assertNotNull(result);
        assertEquals(user.getId(), result.id());
        assertEquals(user.getUsername(), result.userName());
        assertEquals(user.getEmail(), result.email());

        verify(userRepository).findById(user.getId());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void throwAErrorMessageWhenUserByIdWasNotFound(){
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.findUserById(user.getId());
        });

        MatcherAssert.assertThat(exception, notNullValue());
        MatcherAssert.assertThat(exception.getMessage(), is("User With Id "  + user.getId() + " Was Not Found!"));

        verify(userRepository).findById(user.getId());
        verifyNoMoreInteractions(userRepository);
    }


    //----------TestsCreateUser-----------
    @Test
    void createUserWithSuccess(){
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.createUser(createUserDTO);

        assertNotNull(result);
        assertEquals(userDTO.userName(), result.userName());
        assertEquals(userDTO.email(), result.email());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUserWithNullValuesShouldThrowExeption(){
        CreateUserDTO invalidDto = new CreateUserDTO(null, null, "any", null);

        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
            userService.createUser(invalidDto);
        });

        MatcherAssert.assertThat(exception, notNullValue());
        MatcherAssert.assertThat(exception.getMessage(), is("Username, email, password and role cannot be null!"));
        //assertEquals("username, email and password cannot be null!", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }


    //----------TestsUpdateUser-----------
    @Test
    void updateUserWithSuccess(){
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserDTO result = userService.updateUser(user.getId(), editUserDTO);

        assertNotNull(result);
        assertEquals(editUserDTO.userName(), result.userName());
        assertEquals(editUserDTO.email(), result.email());

        verify(userRepository).findById(user.getId());
        verify(userRepository).save(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void updateUserWhenUserNotFoundShouldThrowException(){
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(user.getId(), editUserDTO);
        });

        MatcherAssert.assertThat(exception, notNullValue());
        MatcherAssert.assertThat(exception.getMessage(), is("User With Id "  + user.getId() + " Was Not Found!"));

        verify(userRepository).findById(user.getId());
        verifyNoMoreInteractions(userRepository);
    }


    //----------TestsDeleteUser-----------
    @Test
    void deleteUserWithSuccess(){
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.deleteUser(user.getId());

        verify(userRepository).findById(user.getId());
        verify(userRepository).delete(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void deleteUserWhenUserNotFoundShouldException(){
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(user.getId());
        });

        MatcherAssert.assertThat(exception, notNullValue());
        MatcherAssert.assertThat(exception.getMessage(), is("User With Id "  + user.getId() + " Was Not Found!"));

        verify(userRepository).findById(user.getId());
        verifyNoMoreInteractions(userRepository);
    }

}
