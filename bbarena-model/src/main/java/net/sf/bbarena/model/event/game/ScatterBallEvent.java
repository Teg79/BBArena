package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.Coordinate;
import net.sf.bbarena.model.Direction;
import net.sf.bbarena.model.pitch.Ball;
import net.sf.bbarena.model.pitch.BallMove;
import net.sf.bbarena.model.pitch.SquareDestination;
import net.sf.bbarena.model.util.Concat;
import net.sf.bbarena.model.util.Pair;

public class ScatterBallEvent extends BallEvent {

	private static final long serialVersionUID = -6262949311066708660L;

	private Ball _ball = null;
	private Direction _direction = null;
	private Integer _distance = 1;
	private Coordinate _origin;
	private SquareDestination _destination;
	private BallMove.BallMoveType _type = BallMove.BallMoveType.SCATTER;

	public ScatterBallEvent(int ballId) {
		super(ballId);
	}

	public ScatterBallEvent(int ballId, int direction) {
		this(ballId, direction, 1);
	}
	public ScatterBallEvent(int ballId, int direction, int distance) {
		super(ballId);
		_direction = Direction.getDirection(direction);
		_distance = distance;
	}

	public BallMove.BallMoveType getType() {
		return _type;
	}

	public void setType(BallMove.BallMoveType type) {
		_type = type;
	}

	public Ball getBall() {
		return _ball;
	}

	public void setBall(Ball ball) {
		_ball = ball;
	}

	public void setDirection(Direction direction) {
		_direction = direction;
	}

	public Integer getDistance() {
		return _distance;
	}

	public void setDistance(Integer distance) {
		_distance = distance;
	}

	public Coordinate getOrigin() {
		return _origin;
	}

	public SquareDestination getDestination() {
		return _destination;
	}

	@Override
	protected void doEvent(Arena arena) {
		_ball = getBall(arena);

		_origin = _ball.getSquare().getCoords();
		_destination = arena.getPitch().getDestination(_origin, _direction, _distance);
		arena.getPitch().moveBall(_ball, _direction, _destination.getEffectiveDistance(), _type);
	}

	@Override
	protected void undoEvent(Arena arena) {
		arena.getPitch().moveBall(_ball, _direction.inverse(), _destination.getEffectiveDistance(), _type);
	}

	@Override
	public String getString() {
		return Concat.buildLog(getClass(),
				new Pair("ballId", getBallId()),
				new Pair("direction", _direction),
				new Pair("distance", _distance));
	}

	public Direction getDirection() {
		return _direction;
	}

}
