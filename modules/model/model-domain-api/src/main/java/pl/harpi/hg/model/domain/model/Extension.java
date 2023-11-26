package pl.harpi.hg.model.domain.model;

import lombok.Builder;

@Builder
public record Extension(String groupId, String artifactId, String version) {}
