package com.isaacandrade.blog.domain.projects;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.isaacandrade.blog.domain.technologies.Technology;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "project")
@Table(name = "projects")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Technology> technologies;

    @Column(name = "url")
    private String url;

    @Column(name = "url_github")
    private String urlGitHub;


}
