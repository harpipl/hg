package pl.harpi.hexal.model.app.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import pl.harpi.hexal.model.domain.model.params.NewProjectParameters;
import pl.harpi.hexal.model.domain.ports.inbound.NewProjectUseCase;

import java.util.concurrent.Callable;

@Component
@CommandLine.Command(name = "new")
@RequiredArgsConstructor
public class NewCmd implements Callable<Integer> {
  @CommandLine.Option(
      names = {"-g", "--group"},
      description = "Group",
      required = true)
  private String group;

  @CommandLine.Option(
      names = {"-a", "--artifact"},
      description = "Artifact",
      required = true)
  private String artifact;

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
            .group(group)
            .artifact(artifact)
            .name(name)
            .description(description)
            .build());
    return 0;
  }
}
