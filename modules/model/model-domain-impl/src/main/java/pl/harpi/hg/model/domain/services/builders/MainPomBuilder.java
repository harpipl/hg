package pl.harpi.hg.model.domain.services.builders;

import static pl.harpi.hg.model.domain.Constants.CORE_ARTIFACT_ID;
import static pl.harpi.hg.model.domain.Constants.CORE_GROUP_ID;
import static pl.harpi.hg.model.domain.Constants.CORE_VERSION;
import static pl.harpi.hg.model.domain.Constants.MODEL_VERSION;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import lombok.val;
import pl.harpi.hg.model.domain.model.Dependency;
import pl.harpi.hg.model.domain.model.DependencyManagement;
import pl.harpi.hg.model.domain.model.Parent;
import pl.harpi.hg.model.domain.model.Project;
import pl.harpi.hg.model.domain.model.Repository;
import pl.harpi.hg.model.domain.services.ProjectContext;

@RequiredArgsConstructor(staticName = "of")
public class MainPomBuilder implements Callable<Project> {
  private final ProjectContext context;

  private static final String PROJECT_VERSION = "${project.version}";

  @Override
  public Project call() {
    val parent =
        Parent.builder()
            .groupId(context.parentGroupId())
            .artifactId(context.parentArtifactId())
            .version(context.parentVersion())
            .build();

    val properties = new Hashtable<>();

    properties.put("core.version", CORE_VERSION);

    val repositories = new ArrayList<Repository>();
    repositories.add(
        Repository.builder()
            .id("github")
            .url("https://maven.pkg.github.com/harpipl/packages")
            .build());

    repositories.add(
        Repository.builder().id("central").url("https://repo1.maven.org/maven2").build());

    val dependencies = new ArrayList<Dependency>();

    dependencies.add(
        Dependency.builder()
            .groupId(CORE_GROUP_ID)
            .artifactId(CORE_ARTIFACT_ID)
            .version("${core.version}")
            .type("pom")
            .scope("import")
            .build());

    dependencies.add(
        Dependency.builder()
            .groupId(context.applicationGroupId())
            .artifactId("app")
            .version(PROJECT_VERSION)
            .build());

    if (context.modules() != null) {
      for (var module : context.modules()) {
        dependencies.add(
            Dependency.builder()
                .groupId(context.applicationGroupId())
                .artifactId(module + "-app")
                .version(PROJECT_VERSION)
                .build());
        dependencies.add(
            Dependency.builder()
                .groupId(context.applicationGroupId())
                .artifactId(module + "-domain-api")
                .version(PROJECT_VERSION)
                .build());
        dependencies.add(
            Dependency.builder()
                .groupId(context.applicationGroupId())
                .artifactId(module + "-domain-impl")
                .version(PROJECT_VERSION)
                .build());
        dependencies.add(
            Dependency.builder()
                .groupId(context.applicationGroupId())
                .artifactId(module + "-infrastructure")
                .version(PROJECT_VERSION)
                .build());
      }
    }

    val dm = DependencyManagement.builder().dependencies(dependencies).build();

    return Project.builder()
        .modelVersion(MODEL_VERSION)
        .parent(parent)
        .name(context.applicationName())
        .description(context.applicationDescription())
        .artifactId(context.applicationArtifactId())
        .groupId(context.applicationGroupId())
        .version(context.applicationVersion())
        .packaging("pom")
        .modules(Arrays.asList("app", "modules"))
        .repositories(repositories)
        .properties(properties)
        .dependencyManagement(dm)
        .build();
  }
}
