package org.kornicameister.sise.core.io;

import org.apache.commons.cli.*;
import org.kornicameister.sise.core.Graphs;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * This class wraps around cli utilities from Apache
 * to ensure that all options will be initialized.
 * It is also in control if an options that requires
 * manual input were detected.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class CLIWrapper {
    /**
     * Wrapper around valid options for Puzzle
     */
    private Options options;
    private String helpFooter;

    public static CLIWrapper getCMD() {
        CLIWrapper CLIWrapper = new CLIWrapper();
        Options options = new Options();

        // footer for helper
        StringBuilder builder = new StringBuilder("\nVariables:\n");
        builder.append("order -> array of {L - left, P - right, G - up, D - down} or {R - random}\n");
        builder.append("heuristic_id -> one of the following [1-Manhattan, 2-InvalidCount]\n");
        CLIWrapper.helpFooter = builder.toString();

        //algorithm options
        OptionGroup group = new OptionGroup();
        group.setRequired(true);
        for (Graphs graph : Graphs.values()) {
            group.addOption(Option.builder(graph.getAcronym())
                    .longOpt(graph.name().toLowerCase())
                    .argName("order")
                    .hasArg()
                    .optionalArg(false)
                    .desc(graph.getDesc())
                    .build());
        }
        group.addOption(Option.builder("a")
                .longOpt("a")
                .argName("heuristic_id")
                .hasArgs()
                .numberOfArgs(1)
                .valueSeparator(' ')
                .optionalArg(false)
                .desc("Heuristic")
                .build());
        options.addOptionGroup(group);

        //input options
        group = new OptionGroup();
        group.setRequired(true);
        group.addOption(Option.builder("f")
                .argName("property=value")
                .longOpt("from")
                .hasArgs()
                .numberOfArgs(2)
                .valueSeparator('=')
                .optionalArg(true)
                .desc("Input mode")
                .build());
        options.addOptionGroup(group);

        options.addOption(new Option("help", "print this message"));
        options.addOption(new Option("debug", "print debugging information"));

        CLIWrapper.options = options;

        return CLIWrapper;
    }

    public Map<CLIArguments, Object> parse(String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        Map<CLIArguments, Object> argMap = new HashMap<>();
        try {
            cmd = parser.parse(this.options, args);

            boolean heuristicEnabled = false,
                    cmdInputEnabled = false;
            String strategy = null,
                    inputFilePath = null;
            Integer heuristicId = null;

            // check for help, is so return
            if (cmd.hasOption("help")) {
                this.printHelp();
                return null;
            }

            // determine input method
            if (cmd.hasOption("f")) {
                Properties mode = cmd.getOptionProperties("f");
                if (mode.keySet().contains("cmd")) {
                    cmdInputEnabled = true;
                    argMap.put(CLIArguments.INPUT_C, null);
                } else if (mode.keySet().contains("file")) {
                    inputFilePath = (String) mode.get("file");
                    argMap.put(CLIArguments.INPUT_F, inputFilePath);
                }
                System.out.println(String.format("Input mode detected, [mode=%s,src=%s]",
                        (cmdInputEnabled ? "cmd" : "file"),
                        (inputFilePath == null ? "System.in" : inputFilePath)
                ));
            }

            // determine heuristic
            if (cmd.hasOption("a")) {
                String[] heuristics = cmd.getOptionValues("a");
                heuristicId = Integer.parseInt(heuristics[0]);
                if (heuristicId > 2) {
                    System.err.println(String.format("No heuristic for ID=%d", heuristicId));
                    this.printHelp();
                    return null;
                }
                heuristicEnabled = true;
            }

            // determine algorithm
            for (Graphs graphs : Graphs.values()) {
                String order;
                if (!heuristicEnabled) {
                    if ((order = (String) cmd.getParsedOptionValue(graphs.getAcronym())) != null) {
                        argMap.put(CLIArguments.STRATEGY, graphs);
                        argMap.put(CLIArguments.ORDER, order);
                        System.out.println(String.format("Algorithm detected [algorithm=%s / order=%s]", graphs.name(), order));
                        break;
                    }
                } else {
                    argMap.put(CLIArguments.STRATEGY, Graphs.AStar);
                    argMap.put(CLIArguments.HEURISTIC, heuristicId);
                    System.out.println(String.format("Algorithm detected [algorithm=%s / heuristicId=%d]", Graphs.AStar.name(), heuristicId));
                    break;
                }
            }
        } catch (ParseException e) {
            System.err.println(e.getLocalizedMessage());
            this.printHelp();
        }
        return argMap;
    }

    private void printHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp(400,
                "java -jar Puzzle [options]",
                "",
                this.options,
                this.helpFooter);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CLIWrapper");
        sb.append("{options=").append(options);
        sb.append('}');
        return sb.toString();
    }
}
