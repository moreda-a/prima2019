package prima;

import main.*;

public class DCMAGA_Action extends Action {

	public boolean stay = false;

	public int x;
	public int y;
	public int color;

	public DCMAGA_Action(int x, int y, int c) {
		this.x = x;
		this.y = y;
		color = c;
	}

	public DCMAGA_Action(int x, int y, int c, boolean stay) {
		this.x = x;
		this.y = y;
		this.stay = stay;
		color = c;
	}
}
