package prima;

import java.io.*;
import java.util.*;

import main.*;

public class PRIMA_State extends State {

	static int[] dy = { -1, 0, 0, 1 };
	static int[] dx = { 0, -1, 1, 0 };

	static PII pos[][];
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
		if (obj.getClass() != PRIMA_State.class)
			return false;
		PRIMA_State st = (PRIMA_State) obj;

		boolean res = nextColor == st.nextColor & (parent == null ? st.parent == null : parent.equals(st.parent));
		for (int i = 0; i < width; ++i)
			for (int j = 0; j < height; ++j)
				if (table[i][j] != st.table[i][j])
					res = false;
		return res;
	}

	public PRIMA_Board board;
	public int table[][];
	// public int size;
	public int height;// y
	public int width;// x
	public int playerNumber;
	public int goalNumber;
	public int nextColor;
	public int lastColor;
	public int localLastColor;
	public int localNextColor;

	public int myNumber = -1;

	public PII[] lastMove;
	public PII[] target;

	public int realDepth = 0;
	private Boolean isNotTerminal = null;
	public boolean notChangable = true;

	class PII {
		public PII(int i, int j) {
			first = i;
			second = j;
		}

		public Integer first;
		public Integer second;

		@Override
		public String toString() {
			return "PII [first=" + first + ", second=" + second + "]";
		}

	}

	@Override
	public void reset() {
		super.reset();
		lastColor = -1;
	}

	public PRIMA_State(PRIMA_State st, PRIMA_Action act) {
		width = st.width;
		height = st.height;
		playerNumber = st.playerNumber;
		goalNumber = st.goalNumber;
		table = new int[width][height];
		lastMove = new PII[playerNumber + 1];
		target = new PII[playerNumber + 1];
		for (int i = 1; i <= playerNumber; ++i) {
			lastMove[i] = st.lastMove[i];
			target[i] = st.target[i];
		}
		for (int i = 0; i < width; ++i)
			for (int j = 0; j < height; ++j)
				table[i][j] = st.table[i][j];
		// TODO lolo was here =)))
		if (table[act.y][act.x] >= -1) {
			table[lastMove[act.color].first][lastMove[act.color].second] = 0;
			if (table[act.y][act.x] == -1)
				table[act.y][act.x] = -act.color - 1;
			else
				table[act.y][act.x] = act.color;
		}
		lastMove[act.color] = pos[act.y][act.x];
		parent = st;
		lastColor = st.nextColor;
		localLastColor = st.localNextColor;
		myNumber = st.myNumber;

		setNextColor();
		if (nextColor <= lastColor)
			depth = st.depth + 1;
		else
			depth = st.depth;
		realDepth = st.realDepth;
	}

	public PRIMA_State(String str, int mynum) {
		File file;
		if (PrimaMain.unix)
			file = new File("input/testcase/prima/" + str);
		else
			file = new File("input\\testcase\\prima\\" + str);
		try {
			Scanner sc;
			if (PrimaMain.systemInput)
				sc = new Scanner(System.in);
			else
				sc = new Scanner(file);
			width = sc.nextInt();
			height = sc.nextInt();
			playerNumber = sc.nextInt();
			goalNumber = sc.nextInt();
			table = new int[width][height];
			lastMove = new PII[playerNumber + 1];
			target = new PII[playerNumber + 1];
			for (int i = 1; i <= playerNumber; ++i) {
				lastMove[i] = null;
				target[i] = null;
			}
			pos = new PII[width][height];
			for (int i = 0; i < width; ++i)
				for (int j = 0; j < height; ++j)
					pos[i][j] = new PII(i, j);
			for (int i = 0; i < width; ++i)
				for (int j = 0; j < height; ++j) {
					table[i][j] = sc.nextInt();
					if (table[i][j] != 0 && table[i][j] != -1)
						if (lastMove[table[i][j]] == null)
							lastMove[table[i][j]] = pos[i][j];
					// else
					// target[table[i][j]] = new PII(i, j);
				}
			lastColor = playerNumber;
			localLastColor = playerNumber - 1;
			setNextColor();
			parent = null;
			localLastColor = -1;
			lastColor = -1;
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		nextColor = mynum;
		myNumber = mynum;
	}

	public PRIMA_State(State[] agentState, State[] gg, int myNumber) {
		PRIMA_State st = (PRIMA_State) agentState[1];
		this.myNumber = myNumber;
		width = st.width;
		height = st.height;
		playerNumber = st.playerNumber;
		goalNumber = st.goalNumber;
		table = new int[width][height];
		lastMove = new PII[playerNumber + 1];
		target = new PII[playerNumber + 1];
		for (int i = 1; i <= playerNumber; ++i) {
			lastMove[i] = st.lastMove[i];
			target[i] = st.target[i];
		}
		for (int i = 0; i < width; ++i)
			for (int j = 0; j < height; ++j)
				table[i][j] = st.table[i][j];
		for (int i = 1; i <= playerNumber; ++i) {
			try {
				int help = table[((PRIMA_State) gg[i]).lastMove[i].first][((PRIMA_State) gg[i]).lastMove[i].second];

				if (((PRIMA_State) gg[i]).lastMove[i] != lastMove[i] && (help == 0 || help == -1)) {
					table[lastMove[i].first][lastMove[i].second] = 0;
					table[((PRIMA_State) gg[i]).lastMove[i].first][((PRIMA_State) gg[i]).lastMove[i].second] = help == -1
							? -i - 1
							: i;
					lastMove[i] = ((PRIMA_State) gg[i]).lastMove[i];
				}
			} catch (Exception e) {
				System.out.println(i);
				System.out.println(gg[i]);
				System.out.println(((PRIMA_State) gg[i]).lastMove[i]);
			}
		}
		// table[act.y][act.x] = act.color;
		// lastMove[act.color] = new PII(act.y, act.x);
		parent = st;
		lastColor = st.nextColor;
		localLastColor = st.localNextColor;
		setNextColor();
		if (nextColor <= lastColor)
			depth = st.depth + 1;
		else
			depth = st.depth;
		realDepth = st.realDepth + 1;
	}

	public PRIMA_State(PRIMA_State st) {
		width = st.width;
		height = st.height;
		playerNumber = st.playerNumber;
		goalNumber = st.goalNumber;
		table = new int[width][height];
		lastMove = new PII[playerNumber + 1];
		target = new PII[playerNumber + 1];
		for (int i = 1; i <= playerNumber; ++i) {
			lastMove[i] = st.lastMove[i];
			target[i] = st.target[i];
		}
		for (int i = 0; i < width; ++i)
			for (int j = 0; j < height; ++j)
				table[i][j] = st.table[i][j];
		// TODO lolo was here =)))
		parent = st.parent;
		lastColor = st.lastColor;
		nextColor = st.nextColor;
		localLastColor = st.localLastColor;
		localNextColor = st.localNextColor;

		depth = st.depth;
		realDepth = st.realDepth;
	}

	private void setNextColor() {
		if (PrimaMain.localSolver) {
			for (int i = 0; i < Game.localize.size(); ++i) {
				nextColor = Game.localize.get((i + localLastColor + 1) % Game.localize.size());
				localNextColor = (i + localLastColor + 1) % Game.localize.size();
				int cn = childNumber();
				if (cn == 0)
					continue;
				else
					break;
			}
			return;
		}
		// double best = 1000000;
		// int bestColor = lastColor % playerNumber + 1;
		for (int i = 1; i <= playerNumber; ++i) {
			nextColor = (lastColor + i - 1) % playerNumber + 1;
			int cn = childNumber();
			// int cnt = childNumberTarget();
			// if (isNear(nextColor) || cn == 0)
			if (cn == 0)
				continue;
			else
				break;
//			if (cn == 1) {
//				bestColor = nextColor;
//				break;
//			}
//			if (cnt == 1) {
//				bestColor = nextColor;
//				PII temp = lastMove[nextColor];
//				lastMove[nextColor] = target[nextColor];
//				target[nextColor] = temp;
//				break;
//			}
			// double gn = cn - dis(nextColor) / size * 2;
//			if (best > cn) {
//				best = cn;
//				bestColor = nextColor;
//			} else if (best == cn) {
//				// if (dis(nextColor) > dis(bestColor))
//				// bestColor = nextColor;
//
//			}
		}
		// nextColor = bestColor;
		// System.out.println(lastColor + " - " + nextColor);
	}

	private int dis(int color) {
		return Math.abs(lastMove[color].first - target[color].first)
				+ Math.abs(lastMove[color].second - target[color].second);
	}

	private boolean isNear(int color) {
		// return Math.abs(lastMove[color].first - target[color].first)
		// + Math.abs(lastMove[color].second - target[color].second) == 1;
		return table[lastMove[color].first][lastMove[color].second] < 0;
	}

	@Override
	public boolean isNotTerminal() {
		if (notChangable && isNotTerminal != null)
			return isNotTerminal;
		int res = 0;
		for (int i = 1; i <= playerNumber; ++i) {
			if (!isNear(i))
				break;
			++res;
		}
		if (res == playerNumber || !hasChild() || realDepth + depth >= Game.endTime)
			return isNotTerminal = false;
		return isNotTerminal = true;

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
		res += 1 - (realDepth + depth) / Game.endTime;
		return new PRIMA_Value(-1, res / playerNumber, m);
	}

	@Override
	public String toString() {
		String s = "{";
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j)
				s += table[i][j] + ", ";
			s += (i != width - 1 ? "\n " : "");
		}
		return s + "}";
	}

	@Override
	public ArrayList<State> refreshChilds() {
		ArrayList<State> childss = new ArrayList<State>();
		if (table[lastMove[nextColor].first][lastMove[nextColor].second] < 0)
			childss.add(PRIMA_Simulator.simulateX(this,
					new PRIMA_Action(lastMove[nextColor].second, lastMove[nextColor].first, nextColor, true)));
		else
			for (int i = -1; i < 2; ++i)
				for (int j = (i == 0 ? -1 : 0); j < (i == 0 ? 2 : 1); ++j)
					if (lastMove[nextColor].first + i >= 0 && lastMove[nextColor].first + i < width
							&& lastMove[nextColor].second + j >= 0 && lastMove[nextColor].second + j < height
							&& (table[lastMove[nextColor].first + i][lastMove[nextColor].second + j] == 0
									|| table[lastMove[nextColor].first + i][lastMove[nextColor].second + j] == -1))
						childss.add(PRIMA_Simulator.simulateX(this, new PRIMA_Action(lastMove[nextColor].second + j,
								lastMove[nextColor].first + i, nextColor)));
		return childss;
	}

	private boolean hasChild() {
		if (table[lastMove[nextColor].first][lastMove[nextColor].second] < 0)
			return true;
		for (int i = -1; i < 2; ++i)
			for (int j = (i == 0 ? -1 : 0); j < (i == 0 ? 2 : 1); ++j)
				if (lastMove[nextColor].first + i >= 0 && lastMove[nextColor].first + i < width
						&& lastMove[nextColor].second + j >= 0 && lastMove[nextColor].second + j < height
						&& (table[lastMove[nextColor].first + i][lastMove[nextColor].second + j] == 0
								|| table[lastMove[nextColor].first + i][lastMove[nextColor].second + j] == -1))
					return true;
		return false;
	}

	private int childNumber() {
		// System.out.println(lastMove[1]);
		if (table[lastMove[nextColor].first][lastMove[nextColor].second] < 0)
			return 1;
		int ans = 0;
		for (int i = -1; i < 2; ++i)
			for (int j = (i == 0 ? -1 : 0); j < (i == 0 ? 2 : 1); ++j)
				if (lastMove[nextColor].first + i >= 0 && lastMove[nextColor].first + i < width
						&& lastMove[nextColor].second + j >= 0 && lastMove[nextColor].second + j < height
						&& (table[lastMove[nextColor].first + i][lastMove[nextColor].second + j] == 0
								|| table[lastMove[nextColor].first + i][lastMove[nextColor].second + j] == -1))
					++ans;
		return ans;
	}

	@Override
	public int getDepth() {
		return realDepth;
	}
//	private int childNumberTarget() {
//		PII temp = lastMove[nextColor];
//		lastMove[nextColor] = target[nextColor];
//		target[nextColor] = temp;
//		// int res = childNumber();
//		temp = lastMove[nextColor];
//		lastMove[nextColor] = target[nextColor];
//		target[nextColor] = temp;
//		return res;
//	}

	public void rollDown() {
		// TODO fast random
		long stt = System.currentTimeMillis();
//		Random random = new Random();
//		int v = random.nextInt(childNumber());
		int v = FastMath.rand256() % childNumber();
		// int v = (int) (stt % childNumber());
		int ans = 0;
		PRIMA_Action nextAct = null;
		PrimaMain.shitTimer[13] += System.currentTimeMillis() - stt;
		stt = System.currentTimeMillis();
		if (table[lastMove[nextColor].first][lastMove[nextColor].second] < 0)
			nextAct = new PRIMA_Action(lastMove[nextColor].second, lastMove[nextColor].first, nextColor, true);
		else
			for (int i = 0; i < 4; ++i)
				for (int j = 0; j < 4; ++j) {
					if (lastMove[nextColor].first + dy[i] >= 0 && lastMove[nextColor].first + dy[i] < width
							&& lastMove[nextColor].second + dy[j] >= 0 && lastMove[nextColor].second + dy[j] < height
							&& (table[lastMove[nextColor].first + dy[i]][lastMove[nextColor].second + dy[j]] == 0
									|| table[lastMove[nextColor].first + dy[i]][lastMove[nextColor].second
											+ dy[j]] == -1)) {
						++ans;
						if (ans == v + 1) {
							nextAct = new PRIMA_Action(lastMove[nextColor].second + dy[j],
									lastMove[nextColor].first + dy[i], nextColor);
							break;
						}
					}
				}
		PrimaMain.shitTimer[11] += System.currentTimeMillis() - stt;
		stt = System.currentTimeMillis();
		if (nextAct != null && (table[nextAct.y][nextAct.x] == 0 || table[nextAct.y][nextAct.x] == -1 || nextAct.stay))
			updateDown(nextAct);

		PrimaMain.shitTimer[12] += System.currentTimeMillis() - stt;
		stt = System.currentTimeMillis();
		// state = state.getRandomChild();
	}

	private void updateDown(PRIMA_Action act) {
		// TODO lolo was here =)))
		if (table[act.y][act.x] == -1 || table[act.y][act.x] == 0) {
			table[lastMove[act.color].first][lastMove[act.color].second] = 0;
			if (table[act.y][act.x] == -1)
				table[act.y][act.x] = -act.color - 1;
			else
				table[act.y][act.x] = act.color;
			lastMove[act.color] = pos[act.y][act.x];
		}
		// parent = st;
		// parent lol :D
		lastColor = nextColor;
		localLastColor = localNextColor;

		setNextColor();
		if (nextColor <= lastColor)
			depth = depth + 1;
//		else
//			depth = depth;
//		realDepth = realDepth;

	}

	@Override
	protected void setLocalAgents(Game game) {
		Game.localize = new ArrayList<Integer>();
		for (int i = 1; i <= playerNumber; ++i) {
			if (Math.abs(lastMove[i].first - lastMove[myNumber].first)
					+ Math.abs(lastMove[i].second - lastMove[myNumber].second) < 10)
				Game.localize.add(i);
			// PII pii = new PII(i, j);
		}

	}
}
