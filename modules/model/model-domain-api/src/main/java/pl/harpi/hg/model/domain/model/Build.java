package pl.harpi.hg.model.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record Build(
    String sourceDirectory,
    String scriptSourceDirectory,
    String testSourceDirectory,
    String outputDirectory,
    String testOutputDirectory,
    List<Extension> extensions,
    String defaultGoal,
    List<Resource> resources,
    List<Resource> testResources,
    String directory,
    String finalName,
    List<String> filters,
    PluginManagement pluginManagement,
    List<Plugin> plugins
    ) {}
