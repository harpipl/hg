package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

@Builder
public record ActivationOS(String name, String family, String arch, String version) {}
