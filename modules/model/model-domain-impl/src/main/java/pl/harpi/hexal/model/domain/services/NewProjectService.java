package pl.harpi.hexal.model.domain.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import pl.harpi.core.domain.UseCase;
import pl.harpi.core.domain.exception.GlobalException;
import pl.harpi.hexal.model.domain.model.params.NewProjectParameters;
import pl.harpi.hexal.model.domain.ports.inbound.NewProjectUseCase;
import pl.harpi.hexal.model.domain.ports.outbound.SaveProjectPort;
import pl.harpi.hexal.model.domain.services.builders.MainPomBuilder;

@UseCase
@RequiredArgsConstructor
class NewProjectService implements NewProjectUseCase {
  private static final String WORKING_DIRECTORY = "";

  private final SaveProjectPort saveProjectPort;

  @Override
  public void createNewProject(NewProjectParameters newProjectParameters) throws GlobalException {
    val projectDir =
        Path.of(WORKING_DIRECTORY).toAbsolutePath() + File.separator + newProjectParameters.name();

    val absoluteProjectPath = Path.of(projectDir);

    if (Files.isDirectory(absoluteProjectPath)) {
      throw new GlobalException("Directory '" + newProjectParameters.name() + "' already exists.");
    }

    try {
      Files.createDirectory(absoluteProjectPath);
      Path.of(
              projectDir
                  + File.separator
                  + "app"
                  + File.separator
                  + "src"
                  + File.separator
                  + "main"
                  + File.separator
                  + "java")
          .toFile()
          .mkdirs();
      Path.of(projectDir + File.separator + "modules").toFile().mkdirs();
    } catch (IOException e) {
      throw new GlobalException("IOException", e);
    }

    val mainPomPath = Path.of(projectDir + File.separator + "pom.xml");

    val projectContext =
        ProjectContext.builder()
            .parentVersion("3.1.5")
            .applicationArtifactId(newProjectParameters.artifact())
            .applicationGroupId(newProjectParameters.group())
            .applicationVersion("1.0-SNAPSHOT")
            .coreVersion("1.0.6")
//            .modules(List.of("identity", "verifier"))
            .build();

    saveProjectPort.saveProject(mainPomPath, new MainPomBuilder(projectContext).call());
  }
}
