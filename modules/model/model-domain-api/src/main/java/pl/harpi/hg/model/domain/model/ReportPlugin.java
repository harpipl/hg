package pl.harpi.hg.model.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record ReportPlugin(
    String groupId,
    String artifactId,
    String version,
    List<ReportSet> reportSets,
    String inherited,
    Object configuration) {}
