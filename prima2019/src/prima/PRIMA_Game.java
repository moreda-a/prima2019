package prima;

import main.*;

public class PRIMA_Game extends Game {

	public State[] agentState;

	@Override
	public void init() {
		Centralized = false;
		myState = new PRIMA_State(PrimaMain.testCase, 1);
		PRIMA_State ms = (PRIMA_State) myState;
		agentState = new State[ms.playerNumber + 1];
		agentState[1] = myState;
		for (int i = 2; i <= ms.playerNumber; ++i) {
			agentState[i] = new PRIMA_State(PrimaMain.testCase, i);
		}
	}

	@Override
	public Value CreateZeroValue() {
		return new PRIMA_Value(0, 0);
	}

}