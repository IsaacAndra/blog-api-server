package com.isaacandrade.blog.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user")
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 40)
    private String userName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String passWord;

    public User(CreateUserDTO data) {
        this.userName = data.userName();
        this.email = data.email();
        this.passWord = data.passWord();
    }

    public void updateUser(EditUserDTO data) {
        if (data.userName() != null){
            this.userName = data.userName();
        }
        if (data.email() != null){
            this.email = data.email();
        }
        if (data.passWord() != null){
            this.passWord = data.passWord();
        }
    }
}