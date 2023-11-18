package pl.harpi.hexal.model.domain.model.params;

import lombok.Builder;

@Builder
public record NewProjectParameters(
    String group, String artifact, String name, String description) {}