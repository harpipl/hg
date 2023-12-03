package pl.harpi.hg.model.app.cmd;

import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@Component
@CommandLine.Command(name = "add")
public class ModuleAddCmd implements Callable<Integer> {
  @CommandLine.Parameters(index = "0", description = "The module name.")
  private String name;

  @Override
  public Integer call() throws Exception {
    return 0;
  }
}
