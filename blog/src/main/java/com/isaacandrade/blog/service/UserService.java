package com.isaacandrade.blog.service;

import com.isaacandrade.blog.domain.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<UserDTO> findAllUsers(){
        List<User> user = userRepository.findAll();

        return user.stream().map(this::mapToUserDTO).collect(Collectors.toList());
    }

    public UserDTO findUserById(Long id){
        User userById = userRepository.findById(id).orElseThrow();

        return mapToUserDTO(userById);
    }

    public UserDTO createUser(CreateUserDTO data){
        User user = new User(data);
        User savedUser = userRepository.save(user);

        return mapToUserDTO(savedUser);
    }

    public UserDTO updateUser(Long id, EditUserDTO data){
        User user = userRepository.findById(id).orElseThrow();
        user.updateUser(data);
        userRepository.save(user);

        return mapToUserDTO(user);
    }

    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
    }

    private UserDTO mapToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getUserName(), user.getEmail(), user.getPassWord());
    }

}
