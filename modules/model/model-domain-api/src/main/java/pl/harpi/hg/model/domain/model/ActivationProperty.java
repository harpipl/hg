package pl.harpi.hg.model.domain.model;

import lombok.Builder;

@Builder
public record ActivationProperty(String name, String value) {}
