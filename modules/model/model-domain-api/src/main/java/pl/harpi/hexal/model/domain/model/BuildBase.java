package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record BuildBase(
    String defaultGoal,
    List<Resource> resources,
    List<Resource> testResources,
    String directory,
    String finalName,
    List<String> filters,
    PluginManagement pluginManagement,
    List<Plugin> plugins) {}
