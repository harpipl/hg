package pl.harpi.hg.model.app.cmd;

import picocli.CommandLine;

@CommandLine.Command(name = "module", subcommands = { ModuleAddCmd.class })
public class ModuleCmd {
}
