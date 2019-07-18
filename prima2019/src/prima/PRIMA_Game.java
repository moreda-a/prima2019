package prima;

import main.*;

public class PRIMA_Game extends Game {

	public State[] agentState;

	@Override
	public void init() {
		Centralized = false;
		myState = new PRIMA_State(PrimaMain.testCase, 1);
		PRIMA_State ms = (PRIMA_State) myState;
		Game.endTime = 3 * (ms.width + ms.height) / 2;
		PrimaMain.timer = new long[ms.playerNumber + 1];
		for (int i = 1; i <= ms.playerNumber; ++i)
			PrimaMain.timer[i] = 0;
		// Game.endTime = 22;
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