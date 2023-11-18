package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

import java.util.List;
import java.util.Properties;

@Builder
public record Profile(
    String id,
    Activation activation,
    BuildBase build,
    List<String> modules,
    DistributionManagement distributionManagement,
    Properties properties,
    DependencyManagement dependencyManagement,
    List<Dependency> dependencies,
    List<Repository> repositories,
    List<Repository> pluginRepositories,
    Object reports,
    Reporting reporting) {}
