package xo;

import main.*;

public class XO_Action extends Action {

	public XO_Action(int x, int y, int c) {
		this.x = x;
		this.y = y;
		color = c;
	}

	public int x;
	public int y;
	public int color;
}
