package xo;

import java.util.ArrayList;
import java.util.Random;

import main.*;

public class XO_Simulator extends Simulator {


	public State randomChild(State state) {
		XO_State st = (XO_State) state;
		int v = 0;
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j)
				if (st.table[i][j] == 0)
					v++;
		Random random = new Random();
		v = random.nextInt(v);
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j) {
				if (st.table[i][j] == 0)
					v--;
				if (v == -1)
					return simulate(st, new XO_Action(j, i, 3 - st.lastColor));
			}
		return null;
	}

	@Override
	public State simulate(State state, Action action) {
		XO_Action act = (XO_Action) action;
		XO_State st = (XO_State) state;
		XO_State res = null;
		if (st.table[act.y][act.x] == 0)
			res = new XO_State(st, act);
		return res;
	}

	// @Override
	public ArrayList<Action> getActions(State state) {
		XO_State st = (XO_State) state;
		ArrayList<Action> acs = new ArrayList<Action>();
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j)
				if (st.table[i][j] == 0)
					acs.add(new XO_Action(j, i, 3 - st.lastColor));
		return acs;
	}

}
