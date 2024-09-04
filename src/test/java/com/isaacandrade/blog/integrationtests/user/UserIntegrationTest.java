package com.isaacandrade.blog.integrationtests.user;

import com.isaacandrade.blog.domain.user.CreateUserDTO;
import com.isaacandrade.blog.domain.user.EditUserDTO;
import com.isaacandrade.blog.domain.user.UserDTO;
import com.isaacandrade.blog.domain.user.UserRole;
import com.isaacandrade.blog.exception.UserNotFoundException;
import com.isaacandrade.blog.integrationtests.config.MyIntegrationTest;
import com.isaacandrade.blog.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
public class UserIntegrationTest extends MyIntegrationTest {

    @Autowired
    private UserService userService;


    @Test
    void findAllUsersWithSuccess(){
        //Aqui vamos criar 2 users para serem encontrados pelo findAllUsers
        CreateUserDTO user1 = new CreateUserDTO( "André", "andretest@gmail.com", "andre123", UserRole.ADMIN);
        userService.createUser(user1);
        CreateUserDTO user2 = new CreateUserDTO( "Felipe", "felipeteste@gmail.com", "felipe123", UserRole.ADMIN);
        userService.createUser(user2);

        //Verificando se os usuários foram criados
        assertNotNull(user1);
        assertNotNull(user2);

        //Encontrando a lista dos 2 usuários
        List<UserDTO> users = userService.findAllUsers();

        //Vamos verificar se os 2 usuários estão sendo encontrados corretamente...
        assertNotNull(users);
        assertEquals(2, users.size());

        UserDTO user1Founded = users.get(0);
        assertEquals("André", user1Founded.userName());
        assertEquals("andretest@gmail.com", user1.email());

        UserDTO user2Founded = users.get(1);
        assertEquals("Felipe", user2Founded.userName());
        assertEquals("felipeteste@gmail.com", user2.email());

    }

    @Test
    void findUserByIdWithSuccess(){
        //Criando um novo usuário
        CreateUserDTO user1 = new CreateUserDTO( "André", "andretest@gmail.com", "andre123", UserRole.ADMIN);
        UserDTO createdUser = userService.createUser(user1);
        Long userId = createdUser.id();

        //Verificando se o usuário foi criado
        assertNotNull(createdUser);
        assertNotNull(createdUser.id());

        Optional<UserDTO> userOptional = Optional.of((userService.findUserById(userId)));

        //Verificando se o db está encontrando o usuário pelo Id
        assertTrue(true);
        UserDTO user = userOptional.get();
        assertEquals("André", user.userName());
        assertEquals("andretest@gmail.com", user.email());
    }

    @Test
    void createUserWithSuccess(){
        CreateUserDTO newUser = new CreateUserDTO(
                "Carlos",
                "carlostest@gmail.com",
                "carlos123",
                UserRole.ADMIN
        );

        UserDTO createdUser = userService.createUser(newUser);

        //Vamos verificar se o usuário foi criado corretamente
        assertNotNull(createdUser);
        assertNotNull(createdUser.id());
        assertEquals("Carlos", createdUser.userName());
        assertEquals("carlostest@gmail.com", createdUser.email());

        //Vamos verificar se o usuário será encontrado no db
        Optional<UserDTO> fetchedUser = Optional.of(userService.findUserById(createdUser.id()));
        assertTrue(true);
        assertEquals("Carlos", fetchedUser.get().userName());
        assertEquals("carlostest@gmail.com", fetchedUser.get().email());
    }

    @Test
    void updateUserWithSuccess(){
        //Primeiro vamos criar um usuário para que seja editado.
        CreateUserDTO newUser = new CreateUserDTO("Joao", "joaotest@gmail.com", "joao123", UserRole.ADMIN);
        UserDTO createdUser = userService.createUser(newUser);

        //Agora vamos editar os dados desse usuário para novos:
        EditUserDTO editedUser = new EditUserDTO("Felix", "felixtest@gmail.com", "felix123", UserRole.ADMIN);
        UserDTO updatedUser = userService.updateUser(createdUser.id(), editedUser);

        //Verificando se o usuário foi editado corretamente
        assertNotNull(updatedUser);
        assertNotNull(updatedUser.id());
        assertEquals(createdUser.id(), updatedUser.id());
        assertEquals("Felix", updatedUser.userName());
        assertEquals("felixtest@gmail.com", updatedUser.email());
    }

    @Test
    void deleteUserWithSuccess(){
        //Iremos criar um usuário para ser excluído
        CreateUserDTO newUser = new CreateUserDTO("Jonas", "jonastest@gmail.com", "jonas123", UserRole.ADMIN);
        UserDTO createdUser = userService.createUser(newUser);
        Long userId = createdUser.id();

        //Verificando se o usário foi criado!
        assertNotNull(createdUser);
        assertEquals("Jonas", createdUser.userName());
        assertEquals("jonastest@gmail.com", createdUser.email());


        //Deletando o Usuário
        userService.deleteUser(userId);

        //Verifico a exception UserNotFound é lançada para verificar se o usuário não existe mais no db
        assertThrows(UserNotFoundException.class, () -> {
           userService.findUserById(userId);
        }, "Deve lançar UserNotFoundException ao tentar encontrar um usuário que foi removido do db");
    }
}
