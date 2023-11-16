package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

@Builder
public record Parent(String groupId, String artifactId, String version, String relativePath) {}
