package pl.harpi.hg.model.domain.model.params;

import lombok.Builder;

@Builder
public record NewProjectParameters(
        String groupId, String artifactId, String name, String description) {}
