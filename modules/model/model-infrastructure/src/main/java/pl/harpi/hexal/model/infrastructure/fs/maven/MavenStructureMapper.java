package pl.harpi.hexal.model.infrastructure.fs.maven;

import org.apache.maven.model.Model;
import org.mapstruct.Mapper;
import pl.harpi.hexal.model.domain.model.Project;

@Mapper
public interface MavenStructureMapper {
    Project toDomain(Model model);

    Model fromDomain(Project project);
}
