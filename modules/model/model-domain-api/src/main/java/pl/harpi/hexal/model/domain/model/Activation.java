package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

@Builder
public record Activation(
    Boolean activeByDefault,
    String jdk,
    ActivationOS os,
    ActivationProperty property,
    ActivationFile file) {}
