package main;

import java.util.ArrayList;

public abstract class Game {

	public static ArrayList<Integer> localize = new ArrayList<Integer>();
	public static int endTime;
	protected boolean Centralized;
	protected State myState;

	public abstract void init();

	public abstract Value CreateZeroValue();

	public boolean notEnded() {
		return myState.isNotTerminal();
	}

	public State getState() {
		return myState;
	}

	public void updateState(State newState) {
		myState = newState;
	}

}
