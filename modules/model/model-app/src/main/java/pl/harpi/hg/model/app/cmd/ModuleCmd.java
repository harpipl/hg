package pl.harpi.hg.model.app.cmd;

import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import pl.harpi.hg.model.domain.model.params.NewProjectParameters;
import pl.harpi.hg.model.domain.ports.inbound.NewProjectUseCase;

//@Component
@CommandLine.Command(name = "module", subcommands = { ModuleAddCmd.class })
//@RequiredArgsConstructor
public class ModuleCmd {
}
