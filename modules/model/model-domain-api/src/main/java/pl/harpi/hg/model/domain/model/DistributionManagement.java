package pl.harpi.hg.model.domain.model;

import lombok.Builder;

@Builder
public record DistributionManagement(
    DeploymentRepository repository,
    DeploymentRepository snapshotRepository,
    Site site,
    String downloadUrl,
    Relocation relocation,
    String status) {}
