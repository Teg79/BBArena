package bbarena.model.pitch;

import java.io.Serializable;

import bbarena.model.team.Player;

public class BallMove implements Serializable {

	private static final long serialVersionUID = 3146629616385694103L;

	public enum BallMoveType {
		SCATTER, PASS, KICK_OFF, THROW_IN, PICK_UP, CATCH, OUT, LOSE;
	}

	private Ball ball = null;

	private BallMoveType moveType = null;

	private Square fromSquare = null;

	private Player fromPlayer = null;

	public BallMove(Ball ball, BallMoveType moveType) {
		this.ball = ball;
		this.fromPlayer = ball.getOwner();
		this.fromSquare = ball.getSquare();
		this.moveType = moveType;
	}

	public boolean isFromPlayer() {
		return this.fromPlayer != null;
	}

	public boolean isFromSquare() {
		return this.fromSquare != null;
	}

	public Player getFromPlayer() {
		return fromPlayer;
	}

	public Square getFromSquare() {
		return fromSquare;
	}

	public Ball getBall() {
		return ball;
	}

	public BallMoveType getMoveType() {
		return moveType;
	}

}
