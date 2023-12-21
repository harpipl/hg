package pl.harpi.hg.model.domain.ports.inbound;

import pl.harpi.hg.model.domain.model.params.AddModuleParameters;
import pl.harpi.lib.core.domain.exception.GlobalException;

public interface AddModuleUseCase {
    void addModule(AddModuleParameters addModuleParameters) throws GlobalException;
}
