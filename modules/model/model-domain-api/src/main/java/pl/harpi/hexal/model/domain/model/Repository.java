package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

@Builder
public record Repository(
    RepositoryPolicy releases,
    RepositoryPolicy snapshots,
    String id,
    String name,
    String url,
    String layout) {}
