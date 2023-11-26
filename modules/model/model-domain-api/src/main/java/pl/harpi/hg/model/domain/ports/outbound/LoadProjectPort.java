package pl.harpi.hg.model.domain.ports.outbound;

import java.nio.file.Path;

import pl.harpi.core.domain.exception.GlobalException;
import pl.harpi.hg.model.domain.model.Project;

public interface LoadProjectPort {
    Project loadProject(Path path) throws GlobalException;
}
