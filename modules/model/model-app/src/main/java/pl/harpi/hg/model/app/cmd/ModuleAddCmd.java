package pl.harpi.hg.model.app.cmd;

import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import pl.harpi.hg.model.domain.model.params.AddModuleParameters;
import pl.harpi.hg.model.domain.ports.inbound.AddModuleUseCase;

@Component
@RequiredArgsConstructor
@CommandLine.Command(name = "add")
public class ModuleAddCmd implements Callable<Integer> {
  private final AddModuleUseCase addModuleUseCase;

  @CommandLine.Parameters(index = "0", description = "The module name.")
  private String name;

  @Override
  public Integer call() throws Exception {
    addModuleUseCase.addModule(AddModuleParameters.builder().name(name).build());
    return 0;
  }
}
