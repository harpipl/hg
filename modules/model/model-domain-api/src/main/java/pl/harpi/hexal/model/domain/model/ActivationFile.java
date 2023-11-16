package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

@Builder
public record ActivationFile(String missing, String exists) {}
