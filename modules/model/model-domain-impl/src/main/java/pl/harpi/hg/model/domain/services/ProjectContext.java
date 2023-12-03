package pl.harpi.hg.model.domain.services;

import lombok.Builder;

import java.util.List;

@Builder
public record ProjectContext(
    String parentArtifactId,
    String parentGroupId,
    String parentVersion,
    String applicationName,
    String applicationDescription,
    String applicationArtifactId,
    String applicationGroupId,
    String applicationVersion,
    List<String> modules) {}
