package pl.harpi.hg;

import org.springframework.stereotype.Component;
import picocli.CommandLine;
import pl.harpi.hg.model.app.cmd.NewCmd;

import java.util.concurrent.Callable;

@CommandLine.Command(subcommands = {NewCmd.class})
@Component
class RootCmd implements Callable<Integer> {
  @Override
  public Integer call() {
    return 0;
  }
}
