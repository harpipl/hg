package pl.harpi.hg.model.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record Reporting(
    String excludeDefaults, String outputDirectory, List<ReportPlugin> plugins) {}
