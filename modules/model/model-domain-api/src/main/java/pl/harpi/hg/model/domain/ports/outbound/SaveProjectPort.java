package pl.harpi.hg.model.domain.ports.outbound;

import java.nio.file.Path;
import pl.harpi.core.domain.exception.GlobalException;
import pl.harpi.hg.model.domain.model.Project;

public interface SaveProjectPort {
    void saveProject(Path path, Project project) throws GlobalException;
}
