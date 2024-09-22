package com.isaacandrade.blog.domain.technologies;

import java.util.List;

public record TechnologyDto(
        List<String> frontend,
        List<String> backend
) {

}
