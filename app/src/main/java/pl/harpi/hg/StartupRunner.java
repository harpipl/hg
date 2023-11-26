package pl.harpi.hg;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@RequiredArgsConstructor
class StartupRunner implements CommandLineRunner, ExitCodeGenerator {
  private final CommandLine.IFactory factory;

  private final RootCmd rootCmd;

  private int exitCode;

  @Override
  public void run(String... args) throws Exception {
    exitCode = new CommandLine(rootCmd, factory).execute(args);
  }

  @Override
  public int getExitCode() {
    return exitCode;
  }
}
