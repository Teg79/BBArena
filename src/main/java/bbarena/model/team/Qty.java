package bbarena.model.team;

import java.io.Serializable;

public class Qty implements Serializable {
	
	private static final long serialVersionUID = -4588460230899696960L;
	
	private int min;
	private int max;
	
	Qty(int min, int max) {
		this.min = min;
		this.max = max;
	}

	public int getMax() {
		return max;
	}

	public int getMin() {
		return min;
	}
	
	public String toString() {
		return getMin() + "-" + getMax();
	}
}