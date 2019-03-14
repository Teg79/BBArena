package bbarena.model.event.game;

import bbarena.model.Arena;
import bbarena.model.event.Event;
import bbarena.model.pitch.Ball;

public abstract class BallEvent extends Event {

	private static final long serialVersionUID = -5393891334745678203L;
	protected int _ballId = 0;

	public BallEvent(int ballId) {
		super();
		setBallId(ballId);
	}

	public int getBallId() {
		return getBallId();
	}

	public Ball getBall(Arena arena) {
		return arena.getPitch().getBall(getBallId());
	}

}