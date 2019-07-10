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
				run(game, simulator, mcts);
			}
			break;
		case "COG": // working :D
			game = new SPF_Game();
			simulator = new SPF_Simulator();
			mcts = new MonteCarloTreeSearch(game, simulator);
			for (int i = 1; i <= 6; ++i) {
				Value.modelNumber = i;
				run(game, simulator, mcts);
			}
			break;
		case "PRIMA": // try to make it possible :D
			game = new DCMAGA_Game();
			simulator = new DCMAGA_Simulator();
			mcts = new MonteCarloTreeSearch(game, simulator);
			for (int i = 1; i <= 6; ++i) {
				Value.modelNumber = i;
				run(game, simulator, mcts);
			}
			break;
		default:
			System.out.println("Unknown GAME!! : " + gameName);
			break;
		}
	}

	private static void getConfiguration() {
		File file = new File("input\\configuration.txt");
		try {
			Scanner sc = new Scanner(file);
			systemInput = sc.nextBoolean();
			garbageCollectorMode = sc.nextBoolean();
			debugMode = sc.nextBoolean();
			gameName = sc.next();
			testCase = sc.next();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private static void run(Game game, Simulator simulator, TreeSolver treeSolver) {
		game.init();
		long startTimes = System.currentTimeMillis();
		while (game.notEnded()) {
			State state = game.getState();
			if (PrimaMain.debugMode)
				System.out.println("gameState: \n" + state);
			long startTime = System.currentTimeMillis();
			State nextState = treeSolver.getBestNextState(state);
			if (PrimaMain.debugMode)
				System.out.println(System.currentTimeMillis() - startTime);
			game.updateState(nextState);
		}
		System.out.println("FinalState: \n" + game.getState());
		System.out.println("ModelNUmber: " + Value.modelNumber + " Time: " + (System.currentTimeMillis() - startTimes)
				+ " Ratio: " + game.getState().getValue() + " Depth: " + game.getState().depth);
		// TODO or value ?
	}
}
