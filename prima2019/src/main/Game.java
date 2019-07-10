package main;

public abstract class Game {

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
