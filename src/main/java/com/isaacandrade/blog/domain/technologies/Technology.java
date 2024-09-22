package com.isaacandrade.blog.domain.technologies;

import com.isaacandrade.blog.domain.projects.Project;
import jakarta.persistence.*;

@Entity
@Table(name = "technologies")
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TechnologyType type; // FRONTEND ou BACKEND

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;


}
