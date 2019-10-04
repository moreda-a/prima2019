package main;

public abstract class Value {
	public static int modelNumber = 1;

	public int num;
	public double value;
	protected double bestValue;
	protected boolean mark[];

	public Value(int num, double value) {
		this.num = num;
		this.value = value;
		bestValue = value;
	}

	public Value() {

	}

	public int compareTo(Value vv) {
		if (value < vv.value)
			return -1;
		else
			return 1;
	}

	public int compareToBest(Value vv) {
		if (bestValue < vv.bestValue)
			return -1;
		else
			return 1;
	}

//	public static Value win() {
//		return new Value(-1, 1);
//	}
//
//	public static Value lose() {
//		return new Value(-1, -1);
//	}
//
//	public static Value draw() {
//		return new Value(-1, 0);
//	}

	public abstract int compareTo_UCT(Value vv, int total_number) ;

	// TODO bug in value cast to double make it negative
	public abstract Value update(State state, Value simulationResult);

	@Override
	public String toString() {
		return "{num: " + num + ", value: " + value + ", maxValue: " + bestValue + "}";
	}
}
