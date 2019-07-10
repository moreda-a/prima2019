package prima;

import java.util.ArrayList;

import main.*;

public class PRIMA_Simulator extends Simulator {

	@Override
	public State simulate(State state, Action action) {
		PRIMA_Action act = (PRIMA_Action) action;
		PRIMA_State st = (PRIMA_State) state;
		PRIMA_State res = null;
		if (st.table[act.y][act.x] == 0)
			res = new PRIMA_State(st, act);
		return res;
	}

	public static State simulateX(State state, Action action) {
		PRIMA_Action act = (PRIMA_Action) action;
		PRIMA_State st = (PRIMA_State) state;
		PRIMA_State res = null;
		if (st.table[act.y][act.x] == 0 || st.table[act.y][act.x] == -1 || act.stay)
			res = new PRIMA_State(st, act);
		return res;
	}

	public static ArrayList<Action> getActionsxt(State state) {
		PRIMA_State st = (PRIMA_State) state;
		ArrayList<Action> acs = new ArrayList<Action>();
		if (st.table[st.lastMove[st.nextColor].first][st.lastMove[st.nextColor].second] < 0)
			acs.add(new PRIMA_Action(st.lastMove[st.nextColor].second, st.lastMove[st.nextColor].first, st.nextColor,
					true));
		else
			for (int i = -1; i < 2; ++i)
				for (int j = (i == 0 ? -1 : 0); j < (i == 0 ? 2 : 1); ++j)
					if (st.lastMove[st.nextColor].first + i >= 0 && st.lastMove[st.nextColor].first + i < st.width
							&& st.lastMove[st.nextColor].second + j >= 0
							&& st.lastMove[st.nextColor].second + j < st.height
							&& (st.table[st.lastMove[st.nextColor].first + i][st.lastMove[st.nextColor].second + j] == 0
									|| st.table[st.lastMove[st.nextColor].first + i][st.lastMove[st.nextColor].second
											+ j] == -1))
						acs.add(new PRIMA_Action(st.lastMove[st.nextColor].second + j,
								st.lastMove[st.nextColor].first + i, st.nextColor));
		return acs;
	}

	public ArrayList<Action> getActionst(State state) {
		PRIMA_State st = (PRIMA_State) state;
		ArrayList<Action> acs = new ArrayList<Action>();
		if (st.table[st.lastMove[st.nextColor].first][st.lastMove[st.nextColor].second] < 0)
			acs.add(new PRIMA_Action(st.lastMove[st.nextColor].second, st.lastMove[st.nextColor].first, st.nextColor,
					true));
		else
			for (int i = -1; i < 2; ++i)
				for (int j = (i == 0 ? -1 : 0); j < (i == 0 ? 2 : 1); ++j)
					if (st.lastMove[st.nextColor].first + i >= 0 && st.lastMove[st.nextColor].first + i < st.width
							&& st.lastMove[st.nextColor].second + j >= 0
							&& st.lastMove[st.nextColor].second + j < st.height
							&& (st.table[st.lastMove[st.nextColor].first + i][st.lastMove[st.nextColor].second + j] == 0
									|| st.table[st.lastMove[st.nextColor].first + i][st.lastMove[st.nextColor].second
											+ j] == -1))
						acs.add(new PRIMA_Action(st.lastMove[st.nextColor].second + j,
								st.lastMove[st.nextColor].first + i, st.nextColor));
		return acs;
	}

}
