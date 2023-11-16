package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

@Builder
public record RepositoryPolicy(String enabled, String updatePolicy, String checksumPolicy) {}
