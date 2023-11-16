package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record PluginExecution(String id, String phase, Object goals, String inherited, Object configuration) {}

