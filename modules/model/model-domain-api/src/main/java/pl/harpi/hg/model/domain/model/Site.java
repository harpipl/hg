package pl.harpi.hg.model.domain.model;

import lombok.Builder;

@Builder
public record Site(String id, String name, String url) {}
