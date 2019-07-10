package main;

public abstract class TreeSolver {

//	protected State myState;
//
//	public void setState(State state) {
//		myState = state;
//	}
	protected Simulator simulator;
	protected Game game;

	public TreeSolver(Game game,Simulator simulator) {
		this.game = game;
		this.simulator = simulator;
	}

	abstract public State getBestNextState(State state);

}
