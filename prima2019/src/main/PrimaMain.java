package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import cog.*;
import prima.*;
import xo.*;

public class PrimaMain {
	public static boolean systemInput;
	public static boolean garbageCollectorMode;
	public static boolean debugMode;
	public static String gameName;
	public static String testCase;
	public static boolean unix = true;
	private static boolean[] checkModels = new boolean[7];
	public static boolean fastRollout = true;
	private static boolean debug0 = false;// true;
	public static boolean localSolver = false;
	public static long[] timer;
	public static long[] shitTimer = new long[100];

	public static void main(String[] args) {
		getConfiguration();
		Game game;
		Simulator simulator;
		TreeSolver mcts;
		switch (gameName) {
		case "XO": // not working probably, not checked in long time.
			game = new XO_Game();
			simulator = new XO_Simulator();
			mcts = new MonteCarloTreeSearch(game, simulator);
			for (int i = 1; i <= 6; ++i) {
				Value.modelNumber = i;
				if (checkModels[i])
					run(game, simulator, mcts);
			}
			break;
		case "COG": // working :D
			game = new SPF_Game();
			simulator = new SPF_Simulator();
			mcts = new MonteCarloTreeSearch(game, simulator);
			for (int i = 1; i <= 6; ++i) {
				Value.modelNumber = i;
				if (checkModels[i])
					run(game, simulator, mcts);
			}
			break;
		case "PRIMA": // try to make it possible :D
			game = new PRIMA_Game();
			simulator = new PRIMA_Simulator();
			mcts = new MonteCarloTreeSearch(game, simulator);
			for (int i = 1; i <= 6; ++i) {
				Value.modelNumber = i;
				if (checkModels[i])
					run(game, simulator, mcts);
			}
			break;
		default:
			System.out.println("Unknown GAME!! : " + gameName);
			break;
		}
	}

	private static void getConfiguration() {
		File file;
		if (PrimaMain.unix)
			file = new File("input/configuration.txt");
		else
			file = new File("input\\configuration.txt");
		try {
			Scanner sc = new Scanner(file);
			systemInput = sc.nextBoolean();
			garbageCollectorMode = sc.nextBoolean();
			debugMode = sc.nextBoolean();
			unix = sc.nextBoolean();
			checkModels[1] = sc.nextBoolean();
			checkModels[2] = sc.nextBoolean();
			checkModels[3] = sc.nextBoolean();
			checkModels[4] = sc.nextBoolean();
			checkModels[5] = sc.nextBoolean();
			checkModels[6] = sc.nextBoolean();
			gameName = sc.next();
			testCase = sc.next();
			localSolver = sc.nextBoolean();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private static void run(Game game, Simulator simulator, TreeSolver treeSolver) {// O(pTI^2 + pIT^2 + pTIn^2)
		// O(n^2I^2 + In^4)
		for (int i = 0; i < 100; ++i) {
			shitTimer[i] = 0;
		}
		game.init();
		long startTimes = System.currentTimeMillis();
		while (game.notEnded()) {
			State state = game.getState();

			if (PrimaMain.debugMode || PrimaMain.debug0)
				System.out.println("gameState: \n" + state);
			long startTime = System.currentTimeMillis();

			State nextState = treeSolver.getBestNextState(state);// O(pI^2 + pIT + pIn^2)

			if (PrimaMain.debugMode)
				System.out.println(System.currentTimeMillis() - startTime);

			game.updateState(nextState);
		}
		System.out.println("FinalState: \n" + game.getState());
		System.out.println("ModelNUmber: " + Value.modelNumber + " Time: " + (System.currentTimeMillis() - startTimes)
				+ " Ratio: " + game.getState().getValue() + " Depth: " + game.getState().getDepth());
		long maxx = Long.MIN_VALUE;
		double avg = 0;
		for (int i = 1; i < ((PRIMA_State) game.getState()).playerNumber; ++i) {
			maxx = Math.max(maxx, timer[i]);
			avg = (avg * (i - 1) + timer[i]) / i;
		}
		System.out.println("maxTime: " + maxx + " avgTime: " + avg);
		for (int i = 0; i < 20; ++i) {
			System.out.println("shitTimer " + i + " :" + shitTimer[i]);
		}
		// TODO or value ?
	}
}
