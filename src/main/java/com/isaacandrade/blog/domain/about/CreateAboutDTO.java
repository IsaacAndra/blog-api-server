package com.isaacandrade.blog.domain.about;

import jakarta.validation.constraints.NotBlank;

public record CreateAboutDTO(
        @NotBlank
        String content
) {
}
