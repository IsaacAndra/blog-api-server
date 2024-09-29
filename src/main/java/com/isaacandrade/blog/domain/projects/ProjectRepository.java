package com.isaacandrade.blog.domain.projects;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByName(String name);
}
