package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record Resource(
    String targetPath,
    String filtering,
    String directory,
    List<String> includes,
    List<String> excludes) {}
