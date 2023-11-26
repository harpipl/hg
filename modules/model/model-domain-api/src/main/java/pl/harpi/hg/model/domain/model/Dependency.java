package pl.harpi.hg.model.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record Dependency(
    String groupId,
    String artifactId,
    String version,
    String type,
    String classifier,
    String scope,
    String systemPath,
    List<Exclusion> exclusions) {}
