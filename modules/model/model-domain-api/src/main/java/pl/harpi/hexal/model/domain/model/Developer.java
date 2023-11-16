package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

import java.util.Hashtable;
import java.util.List;

@Builder
public record Developer(
    String id,
    String name,
    String email,
    String url,
    String organization,
    String organizationUrl,
    List<String> roles,
    String timezone,
    Hashtable<Object, Object> properties) {}
