package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record Notifier(
    String type,
    Boolean sendOnError,
    Boolean sendOnFailure,
    Boolean sendOnSuccess,
    Boolean sendOnWarning,
    String address,
    Object configuration) {}
