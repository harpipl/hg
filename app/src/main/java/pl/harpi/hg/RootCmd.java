package pl.harpi.hg;

import java.util.concurrent.Callable;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import pl.harpi.hg.model.app.cmd.ModuleCmd;
import pl.harpi.hg.model.app.cmd.NewCmd;

@CommandLine.Command(subcommands = {NewCmd.class, ModuleCmd.class})
@Component
class RootCmd implements Callable<Integer> {
  @Override
  public Integer call() {
    return 0;
  }
}
