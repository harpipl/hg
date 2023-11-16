package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record Profile(
    String id,
    Activation activation,
    BuildBase build,
    List<String> modules,
    DistributionManagement distributionManagement,
    Object properties,
    DependencyManagement dependencyManagement,
    List<Dependency> dependencies,
    List<Repository> repositories,
    List<Repository> pluginRepositories,
    Object reports,
    Reporting reporting) {}
