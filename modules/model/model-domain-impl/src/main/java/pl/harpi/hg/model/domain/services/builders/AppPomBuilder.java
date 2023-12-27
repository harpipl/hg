package pl.harpi.hg.model.domain.services.builders;

import static pl.harpi.hg.model.domain.Constants.CORE_ARTIFACT_ID;
import static pl.harpi.hg.model.domain.Constants.CORE_GROUP_ID;
import static pl.harpi.hg.model.domain.Constants.MODEL_VERSION;
import static pl.harpi.hg.model.domain.Constants.MODULE_APP;
import static pl.harpi.hg.model.domain.Constants.MODULE_DOMAIN_API;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import lombok.val;
import pl.harpi.hg.model.domain.model.Build;
import pl.harpi.hg.model.domain.model.Dependency;
import pl.harpi.hg.model.domain.model.DomNode;
import pl.harpi.hg.model.domain.model.Parent;
import pl.harpi.hg.model.domain.model.Plugin;
import pl.harpi.hg.model.domain.model.PluginExecution;
import pl.harpi.hg.model.domain.model.Project;
import pl.harpi.hg.model.domain.services.ProjectContext;

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
        .dependencies(
            List.of(
                Dependency.builder()
                    .groupId("org.springframework.boot")
                    .artifactId("spring-boot-starter-web")
                    .build(),
                Dependency.builder().groupId(CORE_GROUP_ID).artifactId(CORE_ARTIFACT_ID + MODULE_APP).build(),
                Dependency.builder()
                    .groupId(CORE_GROUP_ID)
                    .artifactId(CORE_ARTIFACT_ID + MODULE_DOMAIN_API)
                    .build()))
        .build(Build.builder().finalName(context.applicationArtifactId()).plugins(plugins).build())
        .build();
  }
}
