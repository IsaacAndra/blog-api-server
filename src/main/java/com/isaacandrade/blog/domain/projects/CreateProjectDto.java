package com.isaacandrade.blog.domain.projects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.isaacandrade.blog.domain.technologies.TechnologyDto;
@JsonPropertyOrder({"name", "description", "technologies", "url", "url_github"})
public record CreateProjectDto(
        @JsonProperty(value = "name")
        String name,
        @JsonProperty(value = "description")
        String description,

        @JsonProperty(value = "technologies")
        TechnologyDto technologies,

        @JsonProperty(value = "url")
        String url,
        @JsonProperty(value = "url_github")
        String urlGitHub
) {
}
