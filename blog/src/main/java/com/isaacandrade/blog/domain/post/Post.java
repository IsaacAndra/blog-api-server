package com.isaacandrade.blog.domain.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity(name = "post")
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column(name = "is_active")
    private Boolean isActive;

    public Post(CreatePostDTO data) {
        this.title = data.title();
        this.content = data.content();
        this.createdAt = data.createdAt();
        this.isActive = data.isActive();
    }

    public void updatePost(EditPostDTO data) {
        if (data.title() != null){
            this.title = data.title();
        }
        if (data.content() != null){
            this.content = data.content();
        }
        if (data.isActive() != null){
            this.isActive = data.isActive();
        }
    }

    public void delete() {
        this.isActive = false;
    }
}
