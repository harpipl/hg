package pl.harpi.hexal.model.domain.services.builders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import lombok.val;
import pl.harpi.hexal.model.domain.model.Build;
import pl.harpi.hexal.model.domain.model.Dependency;
import pl.harpi.hexal.model.domain.model.DependencyManagement;
import pl.harpi.hexal.model.domain.model.Parent;
import pl.harpi.hexal.model.domain.model.Plugin;
import pl.harpi.hexal.model.domain.model.DomNode;
import pl.harpi.hexal.model.domain.model.Project;
import pl.harpi.hexal.model.domain.model.Repository;
import pl.harpi.hexal.model.domain.services.ProjectContext;

import static pl.harpi.hexal.model.domain.Constants.CORE_ARTIFACT_ID;
import static pl.harpi.hexal.model.domain.Constants.CORE_GROUP_ID;
import static pl.harpi.hexal.model.domain.Constants.CORE_VERSION;
import static pl.harpi.hexal.model.domain.Constants.LOMBOK_MAPSTRUCT_VERSION;
import static pl.harpi.hexal.model.domain.Constants.MAPSTRUCT_VERSION;
import static pl.harpi.hexal.model.domain.Constants.MODEL_VERSION;

@RequiredArgsConstructor(staticName = "of")
public class MainPomBuilder implements Callable<Project> {
  private final ProjectContext context;

  private static final String PROJECT_VERSION = "${project.version}";
  private static final String GROUP_ID = "groupId";
  private static final String ARTIFACT_ID = "artifactId";
  private static final String VERSION = "version";

  @Override
  public Project call() {
    val parent =
        Parent.builder()
            .groupId("org.springframework.boot")
            .artifactId("spring-boot-starter-parent")
            .version(context.parentVersion())
            .build();

    val properties = new Hashtable<>();

    properties.put("core.version", CORE_VERSION);
    properties.put("mapstruct.version", MAPSTRUCT_VERSION);
    properties.put("lombok-mapstruct.version", LOMBOK_MAPSTRUCT_VERSION);

    val repositories = new ArrayList<Repository>();
    repositories.add(
        Repository.builder()
            .id("github-harpipl")
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

    val plugins = new ArrayList<Plugin>();

    val paths = new ArrayList<DomNode>();

    paths.add(
        new DomNode(
            "path",
            null,
            Arrays.asList(
                new DomNode(GROUP_ID, "org.projectlombok"),
                new DomNode(ARTIFACT_ID, "lombok-mapstruct-binding"),
                new DomNode(VERSION, "${lombok-mapstruct.version}"))));

    paths.add(
        new DomNode(
            "path",
            null,
            Arrays.asList(
                new DomNode(GROUP_ID, "org.mapstruct"),
                new DomNode(ARTIFACT_ID, "mapstruct-processor"),
                new DomNode(VERSION, "${mapstruct.version}"))));

    paths.add(
        new DomNode(
            "path",
            null,
            Arrays.asList(
                new DomNode(GROUP_ID, "org.projectlombok"),
                new DomNode(ARTIFACT_ID, "lombok"),
                new DomNode(VERSION, "${lombok.version}"))));

    val children = new ArrayList<DomNode>();

    children.add(new DomNode("source", "${java.version}"));
    children.add(new DomNode("target", "${java.version}"));
    children.add(new DomNode("annotationProcessorPaths", null, paths));
    children.add(
        new DomNode(
            "compilerArgs",
            null,
            Arrays.asList(
                new DomNode("arg", "-Amapstruct.suppressGeneratorTimestamp=true"),
                new DomNode("arg", "-Amapstruct.defaultComponentModel=spring"))));

    plugins.add(
        Plugin.builder()
            .groupId("org.apache.maven.plugins")
            .artifactId("maven-compiler-plugin")
            .version("${maven-compiler-plugin.version}")
            .configuration(new DomNode("configuration", null, children))
            .build());

    return Project.builder()
        .modelVersion(MODEL_VERSION)
        .parent(parent)
        .artifactId(context.applicationArtifactId())
        .groupId(context.applicationGroupId())
        .version(context.applicationVersion())
        .packaging("pom")
        .modules(Arrays.asList("app", "modules"))
        .repositories(repositories)
        .properties(properties)
        .dependencyManagement(dm)
        .build(Build.builder().plugins(plugins).build())
        .build();
  }
}
