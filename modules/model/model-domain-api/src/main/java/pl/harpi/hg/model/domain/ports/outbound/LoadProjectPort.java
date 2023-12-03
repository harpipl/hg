package pl.harpi.hg.model.domain.ports.outbound;

import java.nio.file.Path;

import pl.harpi.hg.model.domain.model.Project;
import pl.harpi.lib.core.domain.exception.GlobalException;

public interface LoadProjectPort {
    Project loadProject(Path path) throws GlobalException;
}
