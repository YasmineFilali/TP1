//Equipe : Filali Yasmine - Chemaou Doha
package fr.univ_montpellier.fsd.sudoku.ppc;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import static org.chocosolver.solver.search.strategy.Search.minDomLBSearch;
import static org.chocosolver.util.tools.ArrayUtils.append;

public class GTSudoku {

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
		instance = 9;
		// Check arguments and options
		for (Option opt : line.getOptions()) {
			checkOption(line, opt.getLongOpt());
		}

		n = instance;
		s = (int) Math.sqrt(n);

		new GTSudoku().solve();
	}

	public void solve() {

		buildModel();
		model.getSolver().showStatistics();
		model.getSolver().solve();
		
		StringBuilder st = new StringBuilder(String.format("Sudoku -- %s\n", instance, " X ", instance));
		st.append("\t");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				st.append(rows[i][j]).append("\t\t\t");
			}
			st.append("\n\t");
		}

		System.out.println(st.toString());
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
			System.out.println(i);
			model.allDifferent(rows[i], "AC").post();
			model.allDifferent(cols[i], "AC").post();
			model.allDifferent(shapes[i], "AC").post();
		}
		
		// --------------------------------------
		// TODO: add constraints here
		//Ligne1
		model.arithm(rows[0][0], "<", rows[0][1]).post();
		model.arithm(rows[0][1], ">", rows[0][2]).post();
		model.arithm(rows[0][3], "<", rows[0][4]).post();
		model.arithm(rows[0][4], "<", rows[0][5]).post();
		model.arithm(rows[0][6], ">", rows[0][7]).post();
		model.arithm(rows[0][7], "<", rows[0][8]).post();
		//Ligne2
		model.arithm(rows[1][0], "<", rows[1][1]).post();
		model.arithm(rows[1][1], "<", rows[1][2]).post();
		model.arithm(rows[1][3], "<", rows[1][4]).post();
		model.arithm(rows[1][4], "<", rows[1][5]).post();
		model.arithm(rows[1][6], "<", rows[1][7]).post();
		model.arithm(rows[1][7], "<", rows[1][8]).post();
		//Ligne3
		model.arithm(rows[2][0], ">", rows[2][1]).post();
		model.arithm(rows[2][1], "<", rows[2][2]).post();
		model.arithm(rows[2][3], ">", rows[2][4]).post();
		model.arithm(rows[2][4], ">", rows[2][5]).post();
		model.arithm(rows[2][6], ">", rows[2][7]).post();
		model.arithm(rows[2][7], "<", rows[2][8]).post();
		//Ligne4
		model.arithm(rows[3][0], ">", rows[3][1]).post();
		model.arithm(rows[3][1], ">", rows[3][2]).post();
		model.arithm(rows[3][1], "<", rows[3][4]).post();
		model.arithm(rows[3][4], ">", rows[3][5]).post();
		model.arithm(rows[3][6], "<", rows[3][7]).post();
		model.arithm(rows[3][7], "<", rows[3][8]).post();
		//Ligne5
		model.arithm(rows[4][0], "<", rows[4][1]).post();
		model.arithm(rows[4][1], ">", rows[4][2]).post();
		model.arithm(rows[4][3], ">", rows[4][4]).post();
		model.arithm(rows[4][4], ">", rows[4][5]).post();
		model.arithm(rows[4][6], ">", rows[4][7]).post();
		model.arithm(rows[4][7], ">", rows[4][8]).post();
		//Ligne6
		model.arithm(rows[5][0], "<", rows[5][1]).post();
		model.arithm(rows[5][1], "<", rows[5][2]).post();
		model.arithm(rows[5][3], "<", rows[5][4]).post();
		model.arithm(rows[5][4], ">", rows[5][5]).post();
		model.arithm(rows[5][6], "<", rows[5][7]).post();
		model.arithm(rows[5][7], ">", rows[5][8]).post();
		//Ligne7
		model.arithm(rows[6][0], ">", rows[6][1]).post();
		model.arithm(rows[6][1], "<", rows[6][2]).post();
		model.arithm(rows[6][3], ">", rows[6][4]).post();
		model.arithm(rows[6][4], ">", rows[6][5]).post();
		model.arithm(rows[6][6], "<", rows[6][7]).post();
		model.arithm(rows[6][7], "<", rows[6][8]).post();
		//Ligne8
		model.arithm(rows[7][0], ">", rows[7][1]).post();
		model.arithm(rows[7][1], ">", rows[7][2]).post();
		model.arithm(rows[7][3], ">", rows[7][4]).post();
		model.arithm(rows[7][4], ">", rows[7][5]).post();
		model.arithm(rows[7][6], ">", rows[7][7]).post();
		model.arithm(rows[7][7], ">", rows[7][8]).post();
		//Ligne9
		model.arithm(rows[8][0], ">", rows[8][1]).post();
		model.arithm(rows[8][1], "<", rows[8][2]).post();
		model.arithm(rows[8][3], ">", rows[8][4]).post();
		model.arithm(rows[8][4], "<", rows[8][5]).post();
		model.arithm(rows[8][6], ">", rows[8][7]).post();
		model.arithm(rows[8][7], ">", rows[8][8]).post();
		//colonne1
		model.arithm(rows[0][0], "<", rows[1][0]).post();
		model.arithm(rows[1][0], ">", rows[2][0]).post();
		model.arithm(rows[3][0], ">", rows[4][0]).post();
		model.arithm(rows[4][0], "<", rows[5][0]).post();
		model.arithm(rows[6][0], "<", rows[7][0]).post();
		model.arithm(rows[7][0], ">", rows[8][0]).post();
		//colonne2
		model.arithm(rows[0][1], "<", rows[1][1]).post();
		model.arithm(rows[1][1], ">", rows[2][1]).post();
		model.arithm(rows[3][1], "<", rows[4][1]).post();
		model.arithm(rows[4][1], ">", rows[5][1]).post();
		model.arithm(rows[6][1], "<", rows[7][1]).post();
		model.arithm(rows[7][1], ">", rows[8][1]).post();
		//colonne3
		model.arithm(rows[0][2], "<", rows[1][2]).post();
		model.arithm(rows[1][2], ">", rows[2][2]).post();
		model.arithm(rows[3][2], "<", rows[4][2]).post();
		model.arithm(rows[4][2], "<", rows[5][2]).post();
		model.arithm(rows[6][2], "<", rows[7][2]).post();
		model.arithm(rows[7][2], ">", rows[8][2]).post();
		//colonne4
		model.arithm(rows[0][3], ">", rows[1][3]).post();
		model.arithm(rows[1][3], "<", rows[2][3]).post();
		model.arithm(rows[3][3], "<", rows[4][3]).post();
		model.arithm(rows[4][3], ">", rows[5][3]).post();
		model.arithm(rows[6][3], "<", rows[7][3]).post();
		model.arithm(rows[7][3], ">", rows[8][3]).post();
		//colonne5
		model.arithm(rows[0][4], ">", rows[1][4]).post();
		model.arithm(rows[1][4], "<", rows[2][4]).post();
		model.arithm(rows[3][4], ">", rows[4][4]).post();
		model.arithm(rows[4][4], "<", rows[5][4]).post();
		model.arithm(rows[6][4], ">", rows[7][4]).post();
		model.arithm(rows[7][4], ">", rows[8][4]).post();
		//colonne6
		model.arithm(rows[0][5], "<", rows[1][5]).post();
		model.arithm(rows[1][5], "<", rows[2][5]).post();
		model.arithm(rows[3][5], ">", rows[4][5]).post();
		model.arithm(rows[4][5], "<", rows[5][5]).post();
		model.arithm(rows[6][5], "<", rows[7][5]).post();
		model.arithm(rows[7][5], "<", rows[8][5]).post();
		//colonne7
		model.arithm(rows[0][6], ">", rows[1][6]).post();
		model.arithm(rows[1][6], "<", rows[2][6]).post();
		model.arithm(rows[3][6], "<", rows[4][6]).post();
		model.arithm(rows[4][6], ">", rows[5][6]).post();
		model.arithm(rows[6][6], ">", rows[7][6]).post();
		model.arithm(rows[7][6], "<", rows[8][6]).post();
		//colonne8
		model.arithm(rows[0][7], ">", rows[1][7]).post();
		model.arithm(rows[1][7], ">", rows[2][7]).post();
		model.arithm(rows[3][7], "<", rows[4][7]).post();
		model.arithm(rows[4][7], "<", rows[5][7]).post();
		model.arithm(rows[6][7], ">", rows[7][7]).post();
		model.arithm(rows[7][7], "<", rows[8][7]).post();
		//colonne9
		model.arithm(rows[0][8], ">", rows[1][8]).post();
		model.arithm(rows[1][8], ">", rows[2][8]).post();
		model.arithm(rows[3][8], ">", rows[4][8]).post();
		model.arithm(rows[4][8], "<", rows[5][8]).post();
		model.arithm(rows[6][8], ">", rows[7][8]).post();
		model.arithm(rows[7][8], "<", rows[8][8]).post();
		// --------------------------------------


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
