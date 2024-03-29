package pl.harpi.hg.model.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record CiManagement(String system, String url, List<Notifier> notifiers) {}
