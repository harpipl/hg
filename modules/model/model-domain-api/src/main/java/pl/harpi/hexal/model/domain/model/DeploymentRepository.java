package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

@Builder
public record DeploymentRepository(
    Boolean uniqueVersion,
    RepositoryPolicy releases,
    RepositoryPolicy snapshots,
    String id,
    String name,
    String url,
    String layout) {}
