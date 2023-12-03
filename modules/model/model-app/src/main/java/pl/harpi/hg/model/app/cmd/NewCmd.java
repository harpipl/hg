package pl.harpi.hg.model.app.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import pl.harpi.hg.model.domain.model.params.NewProjectParameters;
import pl.harpi.hg.model.domain.ports.inbound.NewProjectUseCase;

import java.util.concurrent.Callable;

@Component
@CommandLine.Command(name = "new")
@RequiredArgsConstructor
public class NewCmd implements Callable<Integer> {
  @CommandLine.Option(
      names = {"-g", "--groupId"},
      description = "Group id",
      required = true)
  private String groupId;

  @CommandLine.Option(
      names = {"-a", "--artifactId"},
      description = "Artifact id",
      required = true)
  private String artifactId;

  @CommandLine.Option(
      names = {"-n", "--name"},
      description = "Name",
      required = true)
  private String name;

  @CommandLine.Option(
      names = {"-d", "--description"},
      description = "Description")
  private String description;

  private final NewProjectUseCase newProjectUseCase;

  @Override
  public Integer call() throws Exception {
    newProjectUseCase.createNewProject(
        NewProjectParameters.builder()
            .groupId(groupId)
            .artifactId(artifactId)
            .name(name)
            .description(description)
            .build());
    return 0;
  }
}
