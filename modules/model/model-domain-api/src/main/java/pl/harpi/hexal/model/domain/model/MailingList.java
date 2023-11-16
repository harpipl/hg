package pl.harpi.hexal.model.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record MailingList(
    String name,
    String subscribe,
    String unsubscribe,
    String post,
    String archive,
    List<String> otherArchives) {}
