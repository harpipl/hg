package pl.harpi.hg.model.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record PluginExecution(String id, String phase, List<String> goals, String inherited, Object configuration) {}

