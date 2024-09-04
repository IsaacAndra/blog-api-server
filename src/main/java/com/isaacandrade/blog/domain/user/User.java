package com.isaacandrade.blog.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;

@Entity(name = "user")
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 40, unique = true)
    private String userName;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "password")
    private String passWord;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;



    public User(CreateUserDTO data) {
        this.userName = data.userName();
        this.email = data.email();
        this.passWord = data.passWord();
        this.role = data.role();
    }


    public void updateUser(EditUserDTO data) {
        if (data.userName() != null){
            this.userName = data.userName();
        }
        if (data.email() != null){
            this.email = data.email();
        }
        if (data.passWord() != null) this.passWord = new BCryptPasswordEncoder().encode(data.passWord());
        if (data.role() != null){
            this.role = data.role();
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN)
            return List.of(
                    new SimpleGrantedAuthority(
                            "ROLE_" + this.role.name()),
                    new SimpleGrantedAuthority("ROLE_" + this.role.name())
            );
        else return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }


    @Override
    public String getPassword() {
        return this.passWord;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}