package cog;

import java.util.ArrayList;

import main.*;

public class SPF_Simulator extends Simulator {

	@Override
	public State simulate(State state, Action action) {
		SPF_Action act = (SPF_Action) action;
		SPF_State st = (SPF_State) state;
		SPF_State res = null;
		if (st.table[act.y][act.x] == 0)
			res = new SPF_State(st, act);
		return res;
	}

	public static State simulateX(State state, Action action) {
		SPF_Action act = (SPF_Action) action;
		SPF_State st = (SPF_State) state;
		SPF_State res = null;
		if (st.table[act.y][act.x] == 0)
			res = new SPF_State(st, act);
		return res;
	}

	public static ArrayList<Action> getActionsxt(State state) {
		SPF_State st = (SPF_State) state;
		ArrayList<Action> acs = new ArrayList<Action>();
		for (int i = -1; i < 2; ++i)
			for (int j = (i == 0 ? -1 : 0); j < (i == 0 ? 2 : 1); ++j)
				if (st.lastMove[st.nextColor].first + i >= 0 && st.lastMove[st.nextColor].first + i < st.size
						&& st.lastMove[st.nextColor].second + j >= 0 && st.lastMove[st.nextColor].second + j < st.size
						&& st.table[st.lastMove[st.nextColor].first + i][st.lastMove[st.nextColor].second + j] == 0)
					acs.add(new SPF_Action(st.lastMove[st.nextColor].second + j, st.lastMove[st.nextColor].first + i,
							st.nextColor));
		return acs;
	}

	public ArrayList<Action> getActionst(State state) {
		SPF_State st = (SPF_State) state;
		ArrayList<Action> acs = new ArrayList<Action>();
		for (int i = -1; i < 2; ++i)
			for (int j = (i == 0 ? -1 : 0); j < (i == 0 ? 2 : 1); ++j)
				if (st.lastMove[st.nextColor].first + i >= 0 && st.lastMove[st.nextColor].first + i < st.size
						&& st.lastMove[st.nextColor].second + j >= 0 && st.lastMove[st.nextColor].second + j < st.size
						&& st.table[st.lastMove[st.nextColor].first + i][st.lastMove[st.nextColor].second + j] == 0)
					acs.add(new SPF_Action(st.lastMove[st.nextColor].second + j, st.lastMove[st.nextColor].first + i,
							st.nextColor));
		return acs;
	}

}
