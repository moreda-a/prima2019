package cogplus;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import main.*;

public class SPF_State extends State {

	boolean hashed;
	int hash;

	@Override
	public int hashCode() {
		return hash = (hashed ? hash
				: 31 * Objects.hash(parent, nextColor) + (table == null ? 0 : Arrays.deepHashCode(table)));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj.getClass() != SPF_State.class)
			return false;
		SPF_State st = (SPF_State) obj;

		boolean res = nextColor == st.nextColor & (parent == null ? st.parent == null : parent.equals(st.parent));
		for (int i = 0; i < size; ++i)
			for (int j = 0; j < size; ++j)
				if (table[i][j] != st.table[i][j])
					res = false;
		return res;
	}

	public SPF_Board board;
	public int table[][];
	public int size;
	public int nextColor;
	public int lastColor;
	public int playerNumber;

	public PII[] lastMove;
	public PII[] target;

	class PII {
		public PII(int i, int j) {
			first = i;
			second = j;
		}

		public Integer first;
		public Integer second;

		@Override
		public String toString() {
			return "{" + first + ", " + second + "}";
		}
	}

	@Override
	public void reset() {
		super.reset();
		lastColor = -1;
	}

	public SPF_State(SPF_State st, SPF_Action act) {
		size = st.size;
		playerNumber = st.playerNumber;
		table = new int[size][size];
		lastMove = new PII[playerNumber + 1];
		target = new PII[playerNumber + 1];
		for (int i = 1; i <= playerNumber; ++i) {
			lastMove[i] = st.lastMove[i];
			target[i] = st.target[i];
		}
		for (int i = 0; i < size; ++i)
			for (int j = 0; j < size; ++j)
				table[i][j] = st.table[i][j];
		table[act.y][act.x] = act.color;
		lastMove[act.color] = new PII(act.y, act.x);
		parent = st;
		lastColor = st.nextColor;
		setNextColor();
		depth = st.depth + 1;
	}

	private void setNextColor() {
		double best = 1000000;
		int bestColor = lastColor % playerNumber + 1;
		for (int i = 1; i <= playerNumber; ++i) {
			nextColor = (lastColor + i - 1) % playerNumber + 1;
			int cn = childNumber();
			int cnt = childNumberTarget();
			if (isNear(nextColor) || cn == 0 || cnt == 0)
				continue;
			if (cn == 1) {
				bestColor = nextColor;
				break;
			}
			if (cnt == 1) {
				bestColor = nextColor;
				PII temp = lastMove[nextColor];
				lastMove[nextColor] = target[nextColor];
				target[nextColor] = temp;
				break;
			}
			// double gn = cn - dis(nextColor) / size * 2;
			if (best > cn) {
				best = cn;
				bestColor = nextColor;
			} else if (best == cn) {
				// if (dis(nextColor) > dis(bestColor))
				// bestColor = nextColor;

			}
		}
		nextColor = bestColor;
	}

	private int dis(int color) {
		return Math.abs(lastMove[color].first - target[color].first)
				+ Math.abs(lastMove[color].second - target[color].second);
	}

	private boolean isNear(int color) {
		return Math.abs(lastMove[color].first - target[color].first)
				+ Math.abs(lastMove[color].second - target[color].second) == 1;
	}

	public SPF_State() {
		int size = 5;
		int playerNumber = 5;
		this.playerNumber = playerNumber;
		this.size = size;
		table = new int[size][size];
		lastMove = new PII[playerNumber + 1];
		target = new PII[playerNumber + 1];
		for (int i = 1; i <= playerNumber; ++i) {
			lastMove[i] = null;
			target[i] = null;
		}
		for (int i = 0; i < size; ++i)
			for (int j = 0; j < size; ++j)
				table[i][j] = 0;
//		table[0][1] = 1;
//		table[1][0] = 1;
//		table[0][2] = 2;
//		table[2][2] = 2;
//		lastMove[1] = new PII(0, 1);
//		lastMove[2] = new PII(0, 2);
//		target[1] = new PII(1, 0);
//		target[2] = new PII(2, 2);
		// #testcase2:
		table[0][0] = 1;
		table[2][1] = 1;
		table[1][0] = 2;
		table[3][2] = 2;
		table[2][2] = 3;
		table[0][4] = 3;
		table[1][3] = 4;
		table[4][4] = 4;
		table[4][0] = 5;
		table[2][3] = 5;
		lastMove[1] = new PII(0, 0);
		target[1] = new PII(2, 1);
		lastMove[2] = new PII(1, 0);
		target[2] = new PII(3, 2);
		lastMove[3] = new PII(2, 2);
		target[3] = new PII(0, 4);
		lastMove[4] = new PII(1, 3);
		target[4] = new PII(4, 4);
		lastMove[5] = new PII(4, 0);
		target[5] = new PII(2, 3);
		lastColor = -1;
		nextColor = 1;
//		do {
//			nextColor = nextColor % playerNumber + 1;
//		} while (SPF_Simulator.getActionsx(this).isEmpty() && nextColor != st.nextColor);
		parent = null;
	}

	public SPF_State(String str) {
		File file = new File("testcase\\" + str);
		try {
			Scanner sc;
			if (PrimaMain.systemInput)
				sc = new Scanner(System.in);
			else
				sc = new Scanner(file);
			size = sc.nextInt();
			playerNumber = sc.nextInt();
			table = new int[size][size];
			lastMove = new PII[playerNumber + 1];
			target = new PII[playerNumber + 1];
			for (int i = 1; i <= playerNumber; ++i) {
				lastMove[i] = null;
				target[i] = null;
			}
			for (int i = 0; i < size; ++i)
				for (int j = 0; j < size; ++j) {
					table[i][j] = sc.nextInt();
					if (table[i][j] != 0)
						if (lastMove[table[i][j]] == null)
							lastMove[table[i][j]] = new PII(i, j);
						else
							target[table[i][j]] = new PII(i, j);
				}
			lastColor = playerNumber;
			setNextColor();
			parent = null;
			lastColor = -1;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isNotTerminal() {
		int res = 0;
		for (int i = 1; i <= playerNumber; ++i)
			res += isNear(i) ? 1 : 0;
		if (res == playerNumber)
			return false;
		if (!hasChild())
			return false;
		return true;
	}

	@Override
	public Value getValue() {
		if (isNotTerminal())
			return null;
		double res = 0;
		boolean m[] = new boolean[playerNumber + 1];
		for (int i = 1; i <= playerNumber; ++i) {
			res += isNear(i) ? 1 : 0;
			// res -= isNear(i) ? (double) depth / (2 * size * size) : 0;
			m[i] = isNear(i);
		}

		return new SPF_Value(-1, res / playerNumber, m);
	}

	@Override
	public String toString() {
		String s = "{";
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j)
				s += table[i][j] + ", ";
			s += (i != size - 1 ? "\n " : "");
		}
		return s + "}";
	}

	@Override
	public ArrayList<State> refreshChilds() {
		ArrayList<State> childss = new ArrayList<State>();
		for (int i = -1; i < 2; ++i)
			for (int j = (i == 0 ? -1 : 0); j < (i == 0 ? 2 : 1); ++j)
				if (lastMove[nextColor].first + i >= 0 && lastMove[nextColor].first + i < size
						&& lastMove[nextColor].second + j >= 0 && lastMove[nextColor].second + j < size
						&& table[lastMove[nextColor].first + i][lastMove[nextColor].second + j] == 0)
					childss.add(SPF_Simulator.simulateX(this,
							new SPF_Action(lastMove[nextColor].second + j, lastMove[nextColor].first + i, nextColor)));
		return childss;
	}

	private boolean hasChild() {
		for (int i = -1; i < 2; ++i)
			for (int j = (i == 0 ? -1 : 0); j < (i == 0 ? 2 : 1); ++j)
				if (lastMove[nextColor].first + i >= 0 && lastMove[nextColor].first + i < size
						&& lastMove[nextColor].second + j >= 0 && lastMove[nextColor].second + j < size
						&& table[lastMove[nextColor].first + i][lastMove[nextColor].second + j] == 0)
					return true;
		return false;
	}

	private int childNumber() {
		int ans = 0;
		for (int i = -1; i < 2; ++i)
			for (int j = (i == 0 ? -1 : 0); j < (i == 0 ? 2 : 1); ++j)
				if (lastMove[nextColor].first + i >= 0 && lastMove[nextColor].first + i < size
						&& lastMove[nextColor].second + j >= 0 && lastMove[nextColor].second + j < size
						&& table[lastMove[nextColor].first + i][lastMove[nextColor].second + j] == 0)
					++ans;
		return ans;
	}

	private int childNumberTarget() {
		PII temp = lastMove[nextColor];
		lastMove[nextColor] = target[nextColor];
		target[nextColor] = temp;
		int res = childNumber();
		temp = lastMove[nextColor];
		lastMove[nextColor] = target[nextColor];
		target[nextColor] = temp;
		return res;
	}

	@Override
	protected void setLocalAgents(Game game) {
		// TODO Auto-generated method stub
		
	}

}
