package prima;

import main.*;

public class PRIMA_Value extends Value {

	public PRIMA_Value(int num, double value) {
		super(num, value);
	}

	public PRIMA_Value(int i, double d, boolean[] m) {
		this.num = i;
		this.value = d;
		bestValue = value;
		this.mark = m;
	}

	public int compareTo(PRIMA_Value vv) {
		if (value < vv.value)
			return -1;
		else
			return 1;
	}

	public int compareToBest(PRIMA_Value vv) {
		if (bestValue < vv.bestValue)
			return -1;
		else
			return 1;
	}

	public int compareTo_UCT(PRIMA_Value vv, int total_number) {
		double u1 = value + Math.sqrt(2 * Math.log(total_number) / num);
		double u2 = vv.value + Math.sqrt(2 * Math.log(total_number) / vv.num);
		if (u1 < u2)
			return -1;
		else
			return 1;
	}

	@Override
	public PRIMA_Value update(State state, Value simulationResult) {
		PRIMA_State st = (PRIMA_State) state;
		PRIMA_Value simulation_result = (PRIMA_Value) simulationResult;
		++num;
		// TODO change ?
		// modelNumber = 3;
		bestValue = Math.max(bestValue,
				simulation_result.value
						- (st.lastColor != -1
								? simulation_result.mark[st.lastColor]
										? (double) (1.5 - (double) (((modelNumber - 1) % 3) + 1) / 2)
												* (1 / st.playerNumber)
										: 0
								: 0));
		switch (modelNumber) {
		case 1:
		case 2:
		case 3:
			value = (value * (num - 1) + simulation_result.value
					- (st.lastColor != -1
							? simulation_result.mark[st.lastColor]
									? (double) (1.5 - (double) modelNumber / 2) * (1 / st.playerNumber)
									: 0
							: 0))
					/ num;
//			if (simulation_result.value > 0)
//				System.out.println("dibz:" + simulation_result.value + " - "
//						+ ((double) (1.5 - (double) modelNumber / 2) * (1 / st.playerNumber)));
			break;
		case 4:
		case 5:
		case 6:
			value = bestValue;
			break;
		default:
			break;
		}
		return this;
	}

	@Override
	public String toString() {
		return "{num: " + num + ", value: " + value + ", maxValue: " + bestValue + "}";
	}
}
