package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

@Builder
public record Scm(String connection, String developerConnection, String tag, String url) {}
