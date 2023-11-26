package pl.harpi.hg.model.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record PluginManagement(List<Plugin> plugins) {}
