package pl.harpi.hexal.model.domain.services;

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
import pl.harpi.hexal.model.domain.ports.outbound.LoadProjectPort;

@UseCase
@RequiredArgsConstructor
class NewProjectService implements NewProjectUseCase {
  private static final String WORKING_DIRECTORY = "";

  private final LoadProjectPort loadProjectPort;

  @Override
  public void createNewProject(NewProjectParameters newProjectParameters) throws GlobalException {
    val absoluteProjectPath = Path.of(Path.of(WORKING_DIRECTORY).toAbsolutePath() + File.separator + newProjectParameters.name());

    if (Files.isDirectory(absoluteProjectPath)) {
      throw new GlobalException("Directory '" + newProjectParameters.name() + "' already exists.");
    }

    try {
      Files.createDirectory(absoluteProjectPath);
    } catch (IOException e) {
      throw new GlobalException(e.getMessage());
    }

//    Files.createDirectory(newProjectParameters.applicationName());

//    val project = loadProjectPort.loadProject(Path.of("/Users/areka/IdeaProjects/harpi/hexal/pom.xml"));

//    System.out.println("DOOPA => " + project.artifactId());
  }
}
