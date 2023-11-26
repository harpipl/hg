package pl.harpi.hg.model.infrastructure.fs.maven;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.springframework.stereotype.Component;
import pl.harpi.core.domain.exception.GlobalException;
import pl.harpi.hg.model.domain.model.Project;
import pl.harpi.hg.model.domain.ports.outbound.SaveProjectPort;

@Component
@RequiredArgsConstructor
public class SaveProjectAdapter implements SaveProjectPort {
  private final MavenStructureMapper mavenStructureMapper;
  
  @Override
  public void saveProject(Path path, Project project) throws GlobalException {
    val mavenWriter = new MavenXpp3Writer();
    
    try {
      val fileWriter = new FileWriter(path.toFile());
      val model = mavenStructureMapper.fromDomain(project);

      mavenWriter.write(fileWriter, model);
    } catch (IOException e) {
      throw new GlobalException("Exception saveProject: " + e.getMessage());
    }
  }
}
