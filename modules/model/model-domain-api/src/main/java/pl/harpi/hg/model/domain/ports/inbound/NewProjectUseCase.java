package pl.harpi.hg.model.domain.ports.inbound;

import pl.harpi.core.domain.exception.GlobalException;
import pl.harpi.hg.model.domain.model.params.NewProjectParameters;

public interface NewProjectUseCase {
    void createNewProject(NewProjectParameters newProjectParameters) throws GlobalException;
}
