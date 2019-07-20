package cogplus;

import main.*;

public class SPF_Game extends Game {

	@Override
	public void init() {
		Centralized = true;
		// myState = new SPF_State();
		myState = new SPF_State("testcase10.txt");
	}

	@Override
	public Value CreateZeroValue() {
		return new SPF_Value(0, 0);
	}

}
