package pl.harpi.hexal.model.domain.services.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import lombok.val;
import pl.harpi.hexal.model.domain.model.Build;
import pl.harpi.hexal.model.domain.model.DomNode;
import pl.harpi.hexal.model.domain.model.Parent;
import pl.harpi.hexal.model.domain.model.Plugin;
import pl.harpi.hexal.model.domain.model.PluginExecution;
import pl.harpi.hexal.model.domain.model.Project;
import pl.harpi.hexal.model.domain.services.ProjectContext;

import static pl.harpi.hexal.model.domain.Constants.MODEL_VERSION;

@RequiredArgsConstructor(staticName = "of")
public class AppPomBuilder implements Callable<Project> {
  private final ProjectContext context;

  @Override
  public Project call() {
    val parent =
        Parent.builder()
            .groupId(context.applicationGroupId())
            .artifactId(context.applicationArtifactId())
            .version(context.applicationVersion())
            .build();

    val plugins = new ArrayList<Plugin>();

    plugins.add(
        Plugin.builder()
            .groupId("org.springframework.boot")
            .artifactId("spring-boot-maven-plugin")
            .executions(
                List.of(
                    PluginExecution.builder()
                        .goals(List.of("repackage"))
                        .configuration(
                            new DomNode(
                                "configuration",
                                null,
                                List.of(
                                    new DomNode(
                                        "mainClass",
                                        context.applicationGroupId()
                                            + '.'
                                            + context.applicationName()
                                            + "Application"))))
                        .build()))
            .build());

    return Project.builder()
        .modelVersion(MODEL_VERSION)
        .parent(parent)
        .artifactId("app")
        .build(Build.builder().finalName(context.applicationArtifactId()).plugins(plugins).build())
        .build();
  }
}
