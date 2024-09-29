package com.isaacandrade.blog.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByIsActiveTrue();
    List<Post> findPostsByAuthorId(Long authorId);

    Post findByTitle(String title);
}
