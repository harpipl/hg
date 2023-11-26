package pl.harpi.hg.model.domain.services.builders;

import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import lombok.val;
import pl.harpi.hg.model.domain.model.Parent;
import pl.harpi.hg.model.domain.model.Project;
import pl.harpi.hg.model.domain.services.ProjectContext;

import static pl.harpi.hg.model.domain.Constants.MODEL_VERSION;

@RequiredArgsConstructor(staticName = "of")
public class ModulesPomBuilder implements Callable<Project> {
  private final ProjectContext context;

  @Override
  public Project call() {
    val parent =
        Parent.builder()
            .groupId(context.applicationGroupId())
            .artifactId(context.applicationArtifactId())
            .version(context.applicationVersion())
            .build();

    return Project.builder()
        .modelVersion(MODEL_VERSION)
        .parent(parent)
        .artifactId("modules")
        .packaging("pom")
        .build();
  }
}
