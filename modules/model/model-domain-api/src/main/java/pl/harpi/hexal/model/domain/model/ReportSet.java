package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record ReportSet(
    String id, Object reports, String inherited, Object configuration) {}
