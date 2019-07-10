package xo;

import main.*;

public class XO_Game extends Game {

	@Override
	public void init() {
		Centralized = true;
		myState = new XO_State();
	}

	@Override
	public Value CreateZeroValue() {
		return new XO_Value(0, 0);
	}

}
