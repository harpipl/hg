package pl.harpi.hexal.model.domain.services;

import lombok.Builder;

import java.util.List;

@Builder
public record ProjectContext(
    String parentVersion,
    String applicationName,
    String applicationArtifactId,
    String applicationGroupId,
    String applicationVersion,
    List<String> modules) {}
