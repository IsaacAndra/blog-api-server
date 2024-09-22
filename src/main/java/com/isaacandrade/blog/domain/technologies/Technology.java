package com.isaacandrade.blog.domain.technologies;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.isaacandrade.blog.domain.projects.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "technologies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;



    public Technology(CreateTechnologyDto data) {
        this.name = data.name();
        this.type = data.type();
    }
}
