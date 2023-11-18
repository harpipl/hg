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

@RequiredArgsConstructor
public class MainPomBuilder implements Callable<Project> {
  private final ProjectContext context;

  @Override
  public Project call() {
    val parent =
        Parent.builder()
            .groupId("org.springframework.boot")
            .artifactId("spring-boot-starter-parent")
            .version(context.parentVersion())
            .build();

    val properties = new Hashtable<>();

    properties.put("core.version", context.coreVersion());
    properties.put("mapstruct.version", "1.5.5.Final");
    properties.put("lombok-mapstruct.version", "0.2.0");

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
            .groupId("pl.harpi")
            .artifactId("core")
            .version("${core.version}")
            .type("pom")
            .scope("import")
            .build());

    dependencies.add(
        Dependency.builder()
            .groupId(context.applicationGroupId())
            .artifactId("app")
            .version("${project.version}")
            .build());

    for (var module : context.modules()) {
      dependencies.add(
          Dependency.builder()
              .groupId(context.applicationGroupId())
              .artifactId(module + "-app")
              .version("${project.version}")
              .build());
      dependencies.add(
          Dependency.builder()
              .groupId(context.applicationGroupId())
              .artifactId(module + "-domain-api")
              .version("${project.version}")
              .build());
      dependencies.add(
          Dependency.builder()
              .groupId(context.applicationGroupId())
              .artifactId(module + "-domain-impl")
              .version("${project.version}")
              .build());
      dependencies.add(
          Dependency.builder()
              .groupId(context.applicationGroupId())
              .artifactId(module + "-infrastructure")
              .version("${project.version}")
              .build());
    }

    val dm = DependencyManagement.builder().dependencies(dependencies).build();

    val plugins = new ArrayList<Plugin>();

    val paths = new ArrayList<DomNode>();

    paths.add(
        new DomNode(
            "path",
            null,
            Arrays.asList(
                new DomNode("groupId", "org.projectlombok"),
                new DomNode("artifactId", "lombok-mapstruct-binding"),
                new DomNode("version", "${lombok-mapstruct.version}"))));

    paths.add(
        new DomNode(
            "path",
            null,
            Arrays.asList(
                new DomNode("groupId", "org.mapstruct"),
                new DomNode("artifactId", "mapstruct-processor"),
                new DomNode("version", "${mapstruct.version}"))));

    paths.add(
        new DomNode(
            "path",
            null,
            Arrays.asList(
                new DomNode("groupId", "org.projectlombok"),
                new DomNode("artifactId", "lombok"),
                new DomNode("version", "${lombok.version}"))));

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
