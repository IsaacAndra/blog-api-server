package com.isaacandrade.blog.domain.technologies;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    Technology findByNameAndType(String name, TechnologyType type);

    List<Technology> findByType(TechnologyType type);
}
