package pl.harpi.hexal.model.domain.ports.inbound;

import pl.harpi.core.domain.exception.GlobalException;
import pl.harpi.hexal.model.domain.model.params.NewProjectParameters;

public interface NewProjectUseCase {
    void createNewProject(NewProjectParameters newProjectParameters) throws GlobalException;
}
