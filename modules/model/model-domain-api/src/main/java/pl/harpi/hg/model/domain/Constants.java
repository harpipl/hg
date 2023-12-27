package pl.harpi.hg.model.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
  public static final String MODEL_VERSION = "4.0.0";
  public static final String CORE_GROUP_ID = "pl.harpi";
  public static final String CORE_ARTIFACT_ID = "lib-core";
  public static final String CORE_VERSION = "1.0.1";

  public static final String PARENT_VERSION = "1.0.0";
  public static final String PARENT_GROUP_ID = "pl.harpi";
  public static final String PARENT_ARTIFACT_ID = "spring-boot-parent";

  public static final String MODULE_APP = "-app";
  public static final String MODULE_DOMAIN_API = "-domain-api";
  public static final String MODULE_DOMAIN_IMPL = "-domain-impl";
  public static final String MODULE_INFRASTRUCTURE = "-infrastructure";
}
