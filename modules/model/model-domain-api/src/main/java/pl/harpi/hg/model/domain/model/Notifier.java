package pl.harpi.hg.model.domain.model;

import lombok.Builder;

import java.util.List;
import java.util.Properties;

@Builder
public record Notifier(
    String type,
    Boolean sendOnError,
    Boolean sendOnFailure,
    Boolean sendOnSuccess,
    Boolean sendOnWarning,
    String address,
    Properties configuration) {}
