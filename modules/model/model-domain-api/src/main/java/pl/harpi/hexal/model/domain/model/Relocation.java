package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

@Builder
public record Relocation(String groupId, String artifactId, String version, String message) {}
