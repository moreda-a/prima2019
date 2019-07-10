package main;

import java.util.ArrayList;
import java.util.Random;

public abstract class State {
	public boolean isInTree = false;
	public State parent = null;
	public Value value = null;
	private ArrayList<State> childs = null;
	protected int depth = 0;

	public abstract boolean isNotTerminal();

	public abstract Value getValue();

	public ArrayList<State> getChilds() {// what if change color ? TODO
		if (isInTree) {
			if (childs == null)
				childs = refreshChilds();
			return childs;
		}
		return refreshChilds();
	}

	protected abstract ArrayList<State> refreshChilds();

	public State getRandomChild() {
		ArrayList<State> childss = getChilds();
		Random random = new Random();
		int v = random.nextInt(childss.size());
		if (childss.isEmpty())
			return null;
		return childss.get(v);
	}

	public void reset() {
		isInTree = false;
		parent = null;
		value = null;
		childs = null;

	}
}
