package pl.harpi.hg.model.domain.model;

import lombok.Builder;

@Builder
public record License(String name, String url, String distribution, String comments) {}
