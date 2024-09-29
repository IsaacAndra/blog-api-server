package com.isaacandrade.blog.domain.about;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "about")
@Table(name = "about")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class About {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "content")
    String content;

    public void updateAbout(EditAboutDTO data) {
        if (data.content() == null) {
            this.content = data.content();
        }
    }
}
