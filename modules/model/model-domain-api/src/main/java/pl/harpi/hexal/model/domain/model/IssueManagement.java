package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

@Builder
public record IssueManagement(String system, String url) {}
