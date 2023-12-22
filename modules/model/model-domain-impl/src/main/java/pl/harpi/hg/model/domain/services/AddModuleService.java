package pl.harpi.hg.model.domain.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.val;
import pl.harpi.hg.model.domain.model.Dependency;
import pl.harpi.hg.model.domain.model.Parent;
import pl.harpi.hg.model.domain.model.Project;
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

  private static final String MODULES = "modules";

  private static final String WORKING_DIRECTORY = "";

  private static final String POM_XML = "pom.xml";

  private static final String PROJECT_VERSION = "${project.version}";

  private static final String MODULE_DOMAIN_API = "-domain-api";

  private static final String MODULE_DOMAIN_IMPL = "-domain-impl";

  private static final String MODULE_INFRASTRUCTURE = "-infrastructure";

  private List<String> getModuleNames(AddModuleParameters parameters) {
    return List.of(
        parameters.name() + "-app",
        parameters.name() + MODULE_DOMAIN_API,
        parameters.name() + MODULE_DOMAIN_IMPL,
        parameters.name() + MODULE_INFRASTRUCTURE);
  }

  private Map<String, List<Dependency>> getDependencyMap(
      AddModuleParameters parameters, String parentGroupId) {
    val dependencyMap = new HashMap<String, List<Dependency>>();

    dependencyMap.put(
        parameters.name() + "-app",
        List.of(
            Dependency.builder()
                .groupId(parentGroupId)
                .artifactId(parameters.name() + MODULE_DOMAIN_API)
                .build(),
            Dependency.builder()
                .groupId("org.springframework.boot")
                .artifactId("spring-boot-starter-web")
                .build(),
            Dependency.builder().groupId("org.mapstruct").artifactId("mapstruct").build()));

    val libCoreDomainApiDep =
        Dependency.builder().groupId("pl.harpi").artifactId("lib-core-domain-api").build();

    dependencyMap.put(parameters.name() + MODULE_DOMAIN_API, List.of(libCoreDomainApiDep));

    dependencyMap.put(
        parameters.name() + MODULE_DOMAIN_IMPL,
        List.of(
            libCoreDomainApiDep,
            Dependency.builder()
                .groupId(parentGroupId)
                .artifactId(parameters.name() + MODULE_DOMAIN_API)
                .build()));

    dependencyMap.put(
        parameters.name() + MODULE_INFRASTRUCTURE,
        List.of(
            libCoreDomainApiDep,
            Dependency.builder()
                .groupId(parentGroupId)
                .artifactId(parameters.name() + MODULE_DOMAIN_API)
                .build(),
            Dependency.builder()
                .groupId("org.springframework.boot")
                .artifactId("spring-boot-starter-web")
                .build(),
            Dependency.builder().groupId("org.mapstruct").artifactId("mapstruct").build()));

    return dependencyMap;
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

  private void createModuleStructure(AddModuleParameters parameters, Path path)
      throws GlobalException {
    Path modulePath = Path.of(path + File.separator + parameters.name());

    val parent = loadProjectPort.loadProject(Path.of(path + File.separator + POM_XML));
    val modelVersion = parent.modelVersion();
    val parentGroupId = parent.parent().groupId();
    val parentVersion = parent.parent().version();

    if (Files.isDirectory(modulePath)) {
      throw new GlobalException("Directory '" + parameters.name() + "' already exists.");
    }

    try {
      Files.createDirectory(modulePath);
    } catch (IOException e) {
      throw new GlobalException("IOException", e);
    }

    val moduleProject =
        Project.builder()
            .modelVersion(modelVersion)
            .parent(
                Parent.builder()
                    .groupId(parentGroupId)
                    .artifactId(MODULES)
                    .version(parentVersion)
                    .build())
            .artifactId(parameters.name())
            .packaging("pom")
            .modules(getModuleNames(parameters))
            .build();

    saveProjectPort.saveProject(Path.of(modulePath + File.separator + POM_XML), moduleProject);

    val dependencyMap = getDependencyMap(parameters, parentGroupId);

    val packageMap = new HashMap<String, List<String>>();

    packageMap.put(parameters.name() + "-app", List.of("app"));
    packageMap.put(
        parameters.name() + MODULE_DOMAIN_API,
        List.of("domain.model", "domain.ports.inbound", "domain.ports.outbound"));
    packageMap.put(parameters.name() + MODULE_DOMAIN_IMPL, List.of("domain.services"));
    packageMap.put(parameters.name() + MODULE_INFRASTRUCTURE, List.of("infrastructure"));

    for (var moduleName : getModuleNames(parameters)) {
      try {
        Files.createDirectory(Path.of(modulePath + File.separator + moduleName));

        val prj =
            Project.builder()
                .modelVersion(modelVersion)
                .parent(
                    Parent.builder()
                        .groupId(parentGroupId)
                        .artifactId(parameters.name())
                        .version(parentVersion)
                        .build())
                .artifactId(moduleName)
                .dependencies(dependencyMap.get(moduleName))
                .build();

        saveProjectPort.saveProject(
            Path.of(modulePath + File.separator + moduleName + File.separator + POM_XML), prj);

        for (var pck : packageMap.get(moduleName)) {
          new File(
                  modulePath
                      + File.separator
                      + moduleName
                      + File.separator
                      + "src"
                      + File.separator
                      + "main"
                      + File.separator
                      + "java"
                      + File.separator
                      + parentGroupId.replace(".", File.separator)
                      + File.separator
                      + parameters.name()
                      + File.separator
                      + pck.replace(".", File.separator))
              .mkdirs();
        }
      } catch (IOException e) {
        throw new GlobalException("IOException", e);
      }
    }
  }

  @Override
  public void addModule(AddModuleParameters parameters) throws GlobalException {
    val projectDir = Path.of(WORKING_DIRECTORY).toAbsolutePath();

    val mainPomPath = Path.of(projectDir + File.separator + POM_XML);
    val appPomPath = Path.of(projectDir + File.separator + "app" + File.separator + POM_XML);
    val modulesPomPath = Path.of(projectDir + File.separator + MODULES + File.separator + POM_XML);

    updateMainProject(parameters, mainPomPath);
    updateAppProject(parameters, appPomPath);
    updateModulesProject(parameters, modulesPomPath);

    createModuleStructure(parameters, Path.of(projectDir + File.separator + MODULES));
  }
}
