package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record DependencyManagement(List<Dependency> dependencies) {}
