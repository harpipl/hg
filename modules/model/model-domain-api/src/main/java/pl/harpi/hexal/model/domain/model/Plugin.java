package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record Plugin(
    String groupId,
    String artifactId,
    String version,
    String extensions,
    List<PluginExecution> executions,
    List<Dependency> dependencies,
    Object goals,
    String inherited,
    Object configuration
    ) {}
