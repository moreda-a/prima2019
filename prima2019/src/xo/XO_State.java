package xo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import main.*;

public class XO_State extends State {

	@Override
	public int hashCode() {
		return 31 * Objects.hash(parent, lastColor) + (table == null ? 0 : Arrays.deepHashCode(table));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj.getClass() != XO_State.class)
			return false;
		XO_State st = (XO_State) obj;

		boolean res = lastColor == st.lastColor & (parent == null ? st.parent == null : parent.equals(st.parent));
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j)
				if (table[i][j] != st.table[i][j])
					res = false;
		return res;
	}

	public int table[][] = new int[3][3];
	public int lastColor;

	public XO_State(XO_State st, XO_Action act) {
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j)
				table[i][j] = st.table[i][j];
		table[act.y][act.x] = act.color;
		lastColor = 3 - st.lastColor;
		parent = st;
	}

	public XO_State() {
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j)
				table[i][j] = 0;
		lastColor = 2;
		parent = null;
	}

	@Override
	public boolean isNotTerminal() {
		boolean is1win = (table[0][0] == 1 && table[0][1] == 1 && table[0][2] == 1)
				|| (table[1][0] == 1 && table[1][1] == 1 && table[1][2] == 1)
				|| (table[2][0] == 1 && table[2][1] == 1 && table[2][2] == 1)
				|| (table[0][0] == 1 && table[1][0] == 1 && table[2][0] == 1)
				|| (table[0][1] == 1 && table[1][1] == 1 && table[2][1] == 1)
				|| (table[0][2] == 1 && table[1][2] == 1 && table[2][2] == 1)
				|| (table[0][0] == 1 && table[1][1] == 1 && table[2][2] == 1)
				|| (table[0][2] == 1 && table[1][1] == 1 && table[2][0] == 1);
		boolean is2win = (table[0][0] == 2 && table[0][1] == 2 && table[0][2] == 2)
				|| (table[1][0] == 2 && table[1][1] == 2 && table[1][2] == 2)
				|| (table[2][0] == 2 && table[2][1] == 2 && table[2][2] == 2)
				|| (table[0][0] == 2 && table[1][0] == 2 && table[2][0] == 2)
				|| (table[0][1] == 2 && table[1][1] == 2 && table[2][1] == 2)
				|| (table[0][2] == 2 && table[1][2] == 2 && table[2][2] == 2)
				|| (table[0][0] == 2 && table[1][1] == 2 && table[2][2] == 2)
				|| (table[0][2] == 2 && table[1][1] == 2 && table[2][0] == 2);
		if (is1win || is2win)
			return false;
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j)
				if (table[i][j] == 0)
					return true;
		return false;
	}

	@Override
	public Value getValue() {
		if (isNotTerminal())
			return null;
		boolean is1win = (table[0][0] == 1 && table[0][1] == 1 && table[0][2] == 1)
				|| (table[1][0] == 1 && table[1][1] == 1 && table[1][2] == 1)
				|| (table[2][0] == 1 && table[2][1] == 1 && table[2][2] == 1)
				|| (table[0][0] == 1 && table[1][0] == 1 && table[2][0] == 1)
				|| (table[0][1] == 1 && table[1][1] == 1 && table[2][1] == 1)
				|| (table[0][2] == 1 && table[1][2] == 1 && table[2][2] == 1)
				|| (table[0][0] == 1 && table[1][1] == 1 && table[2][2] == 1)
				|| (table[0][2] == 1 && table[1][1] == 1 && table[2][0] == 1);
		boolean is2win = (table[0][0] == 2 && table[0][1] == 2 && table[0][2] == 2)
				|| (table[1][0] == 2 && table[1][1] == 2 && table[1][2] == 2)
				|| (table[2][0] == 2 && table[2][1] == 2 && table[2][2] == 2)
				|| (table[0][0] == 2 && table[1][0] == 2 && table[2][0] == 2)
				|| (table[0][1] == 2 && table[1][1] == 2 && table[2][1] == 2)
				|| (table[0][2] == 2 && table[1][2] == 2 && table[2][2] == 2)
				|| (table[0][0] == 2 && table[1][1] == 2 && table[2][2] == 2)
				|| (table[0][2] == 2 && table[1][1] == 2 && table[2][0] == 2);
		if (is1win)
			return XO_Value.win();
		else if (is2win)
			return XO_Value.lose();
		else
			return XO_Value.draw();
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j)
				s += table[i][j] + ", ";
			s += '\n';
		}
		return s;
	}

	@Override
	public ArrayList<State> refreshChilds() {
		// TODO Auto-generated method stub
		return null;
	}

}
