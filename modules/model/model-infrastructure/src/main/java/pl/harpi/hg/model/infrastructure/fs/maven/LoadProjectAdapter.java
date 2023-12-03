package pl.harpi.hg.model.infrastructure.fs.maven;

import java.io.FileReader;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.springframework.stereotype.Component;
import pl.harpi.lib.core.domain.exception.GlobalException;
import pl.harpi.hg.model.domain.model.Project;
import pl.harpi.hg.model.domain.ports.outbound.LoadProjectPort;

@Component
@RequiredArgsConstructor
public class LoadProjectAdapter implements LoadProjectPort {
  private final MavenStructureMapper mavenStructureMapper;

  @Override
  public Project loadProject(Path path) throws GlobalException {
    val mavenReader = new MavenXpp3Reader();

    try {
      val fileReader = new FileReader(path.toFile());
      val model = mavenReader.read(fileReader);
      return mavenStructureMapper.toDomain(model);
    } catch (Exception e) {
      throw new GlobalException("Exception loadProject: " + e.getMessage());
    }
  }
}
