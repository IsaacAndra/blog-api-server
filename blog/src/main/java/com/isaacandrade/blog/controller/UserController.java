package com.isaacandrade.blog.controller;

import com.isaacandrade.blog.domain.user.CreateUserDTO;
import com.isaacandrade.blog.domain.user.EditUserDTO;
import com.isaacandrade.blog.domain.user.UserDTO;
import com.isaacandrade.blog.service.UserService;
import com.isaacandrade.blog.utils.MediaType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML
            }
    )
    public ResponseEntity<List<UserDTO>> findAllUsers(){
        List<UserDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long id){
        UserDTO userById = userService.findUserById(id);

        return ResponseEntity.ok(userById);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Transactional
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserDTO data){
        UserDTO creatingUser = userService.createUser(data);

        return new ResponseEntity<>(creatingUser, HttpStatus.CREATED);
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Transactional
    public ResponseEntity<UserDTO> updateUser (@PathVariable Long id, @RequestBody EditUserDTO data){
        UserDTO updatingUser = userService.updateUser(id, data);

        return ResponseEntity.ok(updatingUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser (@PathVariable Long id){
        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
