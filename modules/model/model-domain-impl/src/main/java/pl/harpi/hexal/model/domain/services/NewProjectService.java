package pl.harpi.hexal.model.domain.services;

import static pl.harpi.hexal.model.domain.Constants.SPRING_BOOT_VERSION;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import lombok.val;
import pl.harpi.core.domain.UseCase;
import pl.harpi.core.domain.exception.GlobalException;
import pl.harpi.hexal.model.domain.model.params.NewProjectParameters;
import pl.harpi.hexal.model.domain.ports.inbound.NewProjectUseCase;
import pl.harpi.hexal.model.domain.ports.outbound.SaveProjectPort;
import pl.harpi.hexal.model.domain.services.builders.AppPomBuilder;
import pl.harpi.hexal.model.domain.services.builders.MainPomBuilder;
import pl.harpi.hexal.model.domain.services.builders.ModulesPomBuilder;

@UseCase
@RequiredArgsConstructor
class NewProjectService implements NewProjectUseCase {
  private static final String POM_XML = "pom.xml";

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

    val mainPomPath = Path.of(projectDir + File.separator + POM_XML);
    val appPomPath = Path.of(projectDir + File.separator + "app" + File.separator + POM_XML);
    val modulesPomPath = Path.of(projectDir + File.separator + "modules" + File.separator + POM_XML);

    val projectContext =
        ProjectContext.builder()
            .parentVersion(SPRING_BOOT_VERSION)
            .applicationName(newProjectParameters.name())
            .applicationArtifactId(newProjectParameters.artifact())
            .applicationGroupId(newProjectParameters.group())
            .applicationVersion("1.0-SNAPSHOT")
            .build();

    saveProjectPort.saveProject(mainPomPath, MainPomBuilder.of(projectContext).call());
    saveProjectPort.saveProject(appPomPath, AppPomBuilder.of(projectContext).call());
    saveProjectPort.saveProject(modulesPomPath, ModulesPomBuilder.of(projectContext).call());
  }
}
