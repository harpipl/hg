package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

@Builder
public record Organization(String name, String url) {}
