package pl.harpi.hg.model.domain.services;

import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import pl.harpi.hg.model.domain.model.Dependency;
import pl.harpi.hg.model.domain.model.params.AddModuleParameters;
import pl.harpi.hg.model.domain.ports.inbound.AddModuleUseCase;
import pl.harpi.hg.model.domain.ports.outbound.LoadProjectPort;
import pl.harpi.hg.model.domain.ports.outbound.SaveProjectPort;
import pl.harpi.lib.core.domain.UseCase;
import pl.harpi.lib.core.domain.exception.GlobalException;

@UseCase
@RequiredArgsConstructor
public class AddModuleService implements AddModuleUseCase {
  private final LoadProjectPort loadProjectPort;
  private final SaveProjectPort saveProjectPort;

  private static final String WORKING_DIRECTORY = "";

  private static final String POM_XML = "pom.xml";

  private static final String PROJECT_VERSION = "${project.version}";

  private List<String> getModuleNames(AddModuleParameters addModuleParameters) {
    return List.of(
        addModuleParameters.name() + "-app",
        addModuleParameters.name() + "-domain-api",
        addModuleParameters.name() + "-domain-impl",
        addModuleParameters.name() + "-infrastructure");
  }

  private void removeDuplicates(List<Dependency> dependencies) {
    val set = new HashSet<String>();

    for (int i = dependencies.size() - 1; i >= 0; i--) {
      val dependency = dependencies.get(i);
      val key = dependency.groupId() + "::" + dependency.artifactId();

      if (set.contains(key)) {
        dependencies.remove(i);
      } else {
        set.add(key);
      }
    }
  }

  private void updateMainProject(AddModuleParameters parameters, Path path) throws GlobalException {
    val mainProject = loadProjectPort.loadProject(path);
    val moduleNames = getModuleNames(parameters);

    moduleNames.stream()
        .map(
            moduleName ->
                Dependency.builder()
                    .groupId(mainProject.groupId())
                    .artifactId(moduleName)
                    .version(PROJECT_VERSION)
                    .build())
        .forEach(
            mainDependency ->
                mainProject.dependencyManagement().dependencies().add(mainDependency));

    removeDuplicates(mainProject.dependencyManagement().dependencies());

    saveProjectPort.saveProject(path, mainProject);
  }

  private void updateAppProject(AddModuleParameters parameters, Path path) throws GlobalException {
    val appProject = loadProjectPort.loadProject(path);
    val moduleNames = getModuleNames(parameters);

    moduleNames.stream()
        .map(
            moduleName ->
                Dependency.builder()
                    .groupId(appProject.parent().groupId())
                    .artifactId(moduleName)
                    .build())
        .forEach(mainDependency -> appProject.dependencies().add(mainDependency));

    removeDuplicates(appProject.dependencies());

    saveProjectPort.saveProject(path, appProject);
  }

  private void updateModulesProject(AddModuleParameters parameters, Path path)
      throws GlobalException {
    val project = loadProjectPort.loadProject(path);
    val found = project.modules().stream().anyMatch(module -> module.equals(parameters.name()));

    if (!found) {
      project.modules().add(parameters.name());
      saveProjectPort.saveProject(path, project);
    }
  }

  @Override
  public void addModule(AddModuleParameters parameters) throws GlobalException {
    val projectDir = Path.of(WORKING_DIRECTORY).toAbsolutePath();

    val mainPomPath = Path.of(projectDir + File.separator + POM_XML);
    val appPomPath = Path.of(projectDir + File.separator + "app" + File.separator + POM_XML);
    val modulesPomPath =
        Path.of(projectDir + File.separator + "modules" + File.separator + POM_XML);

    updateMainProject(parameters, mainPomPath);
    updateAppProject(parameters, appPomPath);
    updateModulesProject(parameters, modulesPomPath);
  }
}
