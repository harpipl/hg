package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

@Builder
public record Exclusion(String artifactId, String groupId) {}
