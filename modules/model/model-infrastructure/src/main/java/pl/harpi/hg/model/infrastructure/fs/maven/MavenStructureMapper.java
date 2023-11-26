package pl.harpi.hg.model.infrastructure.fs.maven;

import org.apache.maven.model.Model;
import org.mapstruct.Mapper;
import pl.harpi.hg.model.domain.model.Project;

@Mapper
public interface MavenStructureMapper {
    Project toDomain(Model model);

    Model fromDomain(Project project);
}
