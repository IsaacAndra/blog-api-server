package com.isaacandrade.blog.domain.post;

import com.isaacandrade.blog.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


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
    private LocalDateTime createdAt;

    @JoinColumn(name = "author_id", nullable = false)
    @ManyToOne
    private User author;

    @Column(name = "is_active")
    private Boolean isActive;

    public Post(CreatePostDTO data) {
        this.title = data.title();
        this.content = data.content();
        this.isActive = data.isActive();
    }

    public void updatePost(EditPostDTO data) {
        if (data.title() != null){
            this.title = data.title();
        }
        if (data.content() != null){
            this.content = data.content();
        }
    }

    public void delete() {
        this.isActive = false;
    }


}
