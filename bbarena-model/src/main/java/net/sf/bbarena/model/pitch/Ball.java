package net.sf.bbarena.model.pitch;

import java.io.Serializable;
import java.util.Observable;

import net.sf.bbarena.model.pitch.BallMove.BallMoveType;
import net.sf.bbarena.model.team.Player;

/**
 * In different Pitch/RuleSet you can have one ore more Ball in the pitch with different attributes by extending this class
 * 
 * @author f.bellentani
 */
public class Ball extends Observable implements Serializable {
	
	private static final long serialVersionUID = 6336716131863036650L;
	
	private int id = 0;
	private Player owner = null;
	private Square square = null;
	
	public Ball() {
	}

	public Player getOwner() {
		return owner;
	}

	protected void setOwner(Player owner, BallMoveType moveType) {
		BallMove move = new BallMove(this, moveType);
		this.owner = owner;
		this.square = null;
		setChanged();
		notifyObservers(move);
	}

	public Square getSquare() {
		return square;
	}

	protected void setSquare(Square square, BallMoveType moveType) {
		BallMove move = new BallMove(this, moveType);
		this.square = square;
		this.owner = null;
		setChanged();
		notifyObservers(move);
	}
	
	protected void remove(BallMoveType moveType) {
		BallMove move = new BallMove(this, moveType);
		this.square = null;
		this.owner = null;
		setChanged();
		notifyObservers(move);
	}

	public int getId() {
		return id;
	}
	
	protected void setId(int ballId) {
		this.id = ballId;
	}

}
