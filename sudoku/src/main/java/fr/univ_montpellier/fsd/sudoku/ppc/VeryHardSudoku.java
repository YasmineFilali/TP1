//Equipe : Filali Yasmine - Chemaou Doha
package fr.univ_montpellier.fsd.sudoku.ppc;

import org.apache.commons.cli.*;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import static org.chocosolver.solver.search.strategy.Search.minDomLBSearch;
import static org.chocosolver.util.tools.ArrayUtils.append;

public class VeryHardSudoku {


    static int n;
    static int s;
    private static int instance;
    private static long timeout = 3600000; // one hour

    IntVar[][] rows, cols, shapes;
    Model model;



    public static void main(String[] args) throws ParseException {

        final Options options = configParameters();
        final CommandLineParser parser = new DefaultParser();
        final CommandLine line = parser.parse(options, args);

        boolean helpMode = line.hasOption("help"); // args.length == 0
        if (helpMode) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("sudoku", options, true);
            System.exit(0);
        }
        instance = 16;
        // Check arguments and options
        for (Option opt : line.getOptions()) {
            checkOption(line, opt.getLongOpt());
        }

        n = instance;
        s = (int) Math.sqrt(n);

        new VeryHardSudoku().solve();
        new VeryHardSudoku().solveAll();
    }

    public void solve() {
        buildModel();

        model.getSolver().showStatistics();
        model.getSolver().solve();

        StringBuilder st = new StringBuilder(String.format("Sudoku -- %s\n", instance, " X ", instance));
        st.append("\t");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = rows[i][j].getValue();
                String strValue = Integer.toHexString(value);

                if (value == 16) {
                    strValue = "g";
                }

                st.append(strValue.toUpperCase()).append("\t\t\t");

            }
            st.append("\n\t");
        }

        System.out.println(st.toString());
    }
    public void solveAll() {

        buildModel();

        model.getSolver().showStatistics();
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Toutes les Solutions");

        while (model.getSolver().solve()) {

            StringBuilder st = new StringBuilder(String.format("Sudoku -- %s\n", instance, " X ", instance));
            st.append("\t");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int value = rows[i][j].getValue();
                    String strValue = Integer.toHexString(value);

                    if (value == 16) {
                        strValue = "g";
                    }

                    st.append(strValue.toUpperCase()).append("\t\t\t");
                }
                st.append("\n\t");
            }

            System.out.println(st.toString());

        }
    }


    public void buildModel() {
        model = new Model();

        rows = new IntVar[n][n];
        cols = new IntVar[n][n];
        shapes = new IntVar[n][n];


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                rows[i][j] = model.intVar("c_" + i + "_" + j, 1, n, false);
                cols[j][i] = rows[i][j];
            }
        }
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                for (int k = 0; k < s; k++) {
                    for (int l = 0; l < s; l++) {
                        shapes[j + k * s][i + (l * s)] = rows[l + k * s][i + j * s];
                    }
                }
            }
        }

        for (

                int i = 0; i < n; i++) {
            model.allDifferent(rows[i], "AC").post();
            model.allDifferent(cols[i], "AC").post();
            model.allDifferent(shapes[i], "AC").post();
        }
        // --------------------------------------
        // TODO: add constraints here
        for (int i = 0 ; i < n ; i++)
            for (int j = 0 ; j < n ; j++) {
                //ligne 1
                model.arithm(rows[0][1], "=", 16).post();
                model.arithm(rows[0][4], "=", 15).post();
                model.arithm(rows[0][5], "=", 8).post();
                model.arithm(rows[0][6], "=", 9).post();
                model.arithm(rows[0][7], "=", 6).post();
                model.arithm(rows[0][8], "=", 4).post();
                model.arithm(rows[0][9], "=", 11).post();
                model.arithm(rows[0][10], "=", 13).post();
                model.arithm(rows[0][11], "=", 5).post();
                model.arithm(rows[0][14], "=", 3).post();
                //ligne2
                model.arithm(rows[1][0], "=", 6).post();
                model.arithm(rows[1][1], "=", 12).post();
                model.arithm(rows[1][6], "=", 4).post();
                model.arithm(rows[1][7], "=", 14).post();
                model.arithm(rows[1][8], "=", 2).post();
                model.arithm(rows[1][9], "=", 7).post();
                model.arithm(rows[1][14], "=", 5).post();
                model.arithm(rows[1][15], "=", 9).post();
                //ligne3
                model.arithm(rows[2][3], "=", 13).post();
                model.arithm(rows[2][6], "=", 16).post();
                model.arithm(rows[2][7], "=", 7).post();
                model.arithm(rows[2][8], "=", 15).post();
                model.arithm(rows[2][9], "=", 14).post();
                model.arithm(rows[2][12], "=", 6).post();
                //ligne4
                model.arithm(rows[3][2], "=", 4).post();
                model.arithm(rows[3][3], "=", 3).post();
                model.arithm(rows[3][4], "=", 10).post();
                model.arithm(rows[3][11], "=", 6).post();
                model.arithm(rows[3][12], "=", 1).post();
                model.arithm(rows[3][13], "=", 11).post();
                //ligne5
                model.arithm(rows[4][0], "=", 7).post();
                model.arithm(rows[4][3], "=", 5).post();
                model.arithm(rows[4][4], "=", 8).post();
                model.arithm(rows[4][5], "=", 15).post();
                model.arithm(rows[4][10], "=", 11).post();
                model.arithm(rows[4][11], "=", 14).post();
                model.arithm(rows[4][12], "=", 9).post();
                model.arithm(rows[4][15], "=", 16).post();

                //ligne6
                model.arithm(rows[5][0], "=", 8).post();
                model.arithm(rows[5][4], "=", 9).post();
                model.arithm(rows[5][7], "=", 4).post();
                model.arithm(rows[5][8], "=", 13).post();
                model.arithm(rows[5][11], "=", 3).post();
                model.arithm(rows[5][15], "=", 2).post();


                //ligne7
                model.arithm(rows[6][0], "=", 12).post();
                model.arithm(rows[6][1], "=", 1).post();
                model.arithm(rows[6][2], "=", 3).post();
                model.arithm(rows[6][6], "=", 6).post();
                model.arithm(rows[6][9], "=", 16).post();
                model.arithm(rows[6][13], "=", 15).post();
                model.arithm(rows[6][14], "=", 4).post();
                model.arithm(rows[6][15], "=", 5).post();

                //ligne8
                model.arithm(rows[7][0], "=", 9).post();
                model.arithm(rows[7][1], "=", 13).post();
                model.arithm(rows[7][2], "=", 11).post();
                model.arithm(rows[7][5], "=", 16).post();
                model.arithm(rows[7][10], "=", 15).post();
                model.arithm(rows[7][13], "=", 7).post();
                model.arithm(rows[7][14], "=", 10).post();
                model.arithm(rows[7][15], "=", 6).post();

                //ligne9
                model.arithm(rows[8][0], "=", 16).post();
                model.arithm(rows[8][1], "=", 11).post();
                model.arithm(rows[8][2], "=", 10).post();
                model.arithm(rows[8][5], "=", 2).post();
                model.arithm(rows[8][10], "=", 7).post();
                model.arithm(rows[8][13], "=", 5).post();
                model.arithm(rows[8][14], "=", 6).post();
                model.arithm(rows[8][15], "=", 13).post();
                //ligne10
                model.arithm(rows[9][0], "=", 5).post();
                model.arithm(rows[9][1], "=", 6).post();
                model.arithm(rows[9][2], "=", 15).post();
                model.arithm(rows[9][6], "=", 10).post();
                model.arithm(rows[9][9], "=", 2).post();
                model.arithm(rows[9][13], "=", 8).post();
                model.arithm(rows[9][14], "=", 7).post();
                model.arithm(rows[9][15], "=", 4).post();
                //ligne11
                model.arithm(rows[10][0], "=", 13).post();
                model.arithm(rows[10][4], "=", 6).post();
                model.arithm(rows[10][7], "=", 9).post();
                model.arithm(rows[10][8], "=", 5).post();
                model.arithm(rows[10][11], "=", 16).post();
                model.arithm(rows[10][15], "=", 15).post();
               //ligne12
                model.arithm(rows[11][0], "=", 3).post();
                model.arithm(rows[11][3], "=", 12).post();
                model.arithm(rows[11][4], "=", 11).post();
                model.arithm(rows[11][5], "=", 5).post();
                model.arithm(rows[11][10], "=", 10).post();
                model.arithm(rows[11][11], "=", 4).post();
                model.arithm(rows[11][12], "=", 16).post();
                model.arithm(rows[11][15], "=", 1).post();
                //ligne13
                model.arithm(rows[12][2], "=", 9).post();
                model.arithm(rows[12][3], "=", 6).post();
                model.arithm(rows[12][4], "=", 16).post();
                model.arithm(rows[12][11], "=", 7).post();
                model.arithm(rows[12][12], "=", 2).post();
                model.arithm(rows[12][13], "=", 12).post();
                //ligne14
                model.arithm(rows[13][3], "=", 16).post();
                model.arithm(rows[13][6], "=", 11).post();
                model.arithm(rows[13][7], "=", 13).post();
                model.arithm(rows[13][8], "=", 12).post();
                model.arithm(rows[13][9], "=", 5).post();
                model.arithm(rows[13][12], "=", 15).post();
                //ligne15
                model.arithm(rows[14][0], "=", 4).post();
                model.arithm(rows[14][1], "=", 3).post();
                model.arithm(rows[14][6], "=", 8).post();
                model.arithm(rows[14][7], "=", 2).post();
                model.arithm(rows[14][8], "=", 16).post();
                model.arithm(rows[14][9], "=", 15).post();
                model.arithm(rows[14][14], "=", 1).post();
                model.arithm(rows[14][15], "=", 7).post();
                //ligne16
                model.arithm(rows[15][1], "=", 8).post();
                model.arithm(rows[15][4], "=", 5).post();
                model.arithm(rows[15][5], "=", 9).post();
                model.arithm(rows[15][6], "=", 14).post();
                model.arithm(rows[15][7], "=", 10).post();
                model.arithm(rows[15][8], "=", 1).post();
                model.arithm(rows[15][9], "=", 3).post();
                model.arithm(rows[15][10], "=", 2).post();
                model.arithm(rows[15][11], "=", 13).post();
                model.arithm(rows[15][14], "=", 16).post();


            }


    }

    // Check all parameters values
    public static void checkOption(CommandLine line, String option) {

        switch (option) {
            case "inst":
                instance = Integer.parseInt(line.getOptionValue(option));
                break;
            case "timeout":
                timeout = Long.parseLong(line.getOptionValue(option));
                break;
            default: {
                System.err.println("Bad parameter: " + option);
                System.exit(2);
            }

        }

    }

    // Add options here
    private static Options configParameters() {

        final Option helpFileOption = Option.builder("h").longOpt("help").desc("Display help message").build();

        final Option instOption = Option.builder("i").longOpt("instance").hasArg(true).argName("sudoku instance")
                .desc("sudoku instance size").required(false).build();

        final Option limitOption = Option.builder("t").longOpt("timeout").hasArg(true).argName("timeout in ms")
                .desc("Set the timeout limit to the specified time").required(false).build();

        // Create the options list
        final Options options = new Options();
        options.addOption(instOption);
        options.addOption(limitOption);
        options.addOption(helpFileOption);

        return options;
    }

    public void configureSearch() {
        model.getSolver().setSearch(minDomLBSearch(append(rows)));

    }

}

