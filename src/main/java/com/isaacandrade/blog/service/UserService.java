package com.isaacandrade.blog.service;

import com.isaacandrade.blog.domain.user.*;
import com.isaacandrade.blog.exception.ConstraintViolationException;
import com.isaacandrade.blog.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<UserDTO> findAllUsers(){
        List<User> user = userRepository.findAll();
        if (user.isEmpty()){
            throw new UserNotFoundException();
        }

        return user.stream().map(this::mapToUserDTO).collect(Collectors.toList());
    }

    public UserDTO findUserById(Long id){
        User userById = userRepository
                .findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException("User With Id "  + id + " Was Not Found!")
                );

        return mapToUserDTO(userById);
    }

    public UserDTO createUser(CreateUserDTO data){

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.passWord());
        CreateUserDTO newUser = new CreateUserDTO(data.userName(), data.email(), encryptedPassword, data.role());

        if (userRepository.findByUserName(data.userName()) != null){
            throw new ConstraintViolationException("This username already exist!");
        }
        if (data.userName() == null || data.email() == null || data.passWord() == null || data.role() == null){
            throw new ConstraintViolationException("Username, email, password and role cannot be null!");
        }

        User user = new User(newUser);
        User savedUser = userRepository.save(user);

        return mapToUserDTO(savedUser);
    }

    public UserDTO updateUser(Long id, EditUserDTO data){
        User user = userRepository
                .findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException("User With Id "  + id + " Was Not Found!"));

        user.updateUser(data);
        userRepository.save(user);

        return mapToUserDTO(user);
    }

    public void deleteUser(Long id){
        User user = userRepository
                .findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException("User With Id "  + id + " Was Not Found!"));
        userRepository.delete(user);
    }

    private UserDTO mapToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getUsername(), user.getRole());
    }

}
