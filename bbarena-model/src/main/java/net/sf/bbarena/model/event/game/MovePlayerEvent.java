package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.Direction;
import net.sf.bbarena.model.pitch.Pitch;
import net.sf.bbarena.model.team.Player;
import net.sf.bbarena.model.util.Concat;
import net.sf.bbarena.model.util.Pair;

public class MovePlayerEvent extends PlayerEvent {

	private static final long serialVersionUID = 2911617554463404375L;

	private Player _player = null;
	private Direction _direction = null;
	private int _range = 1;

	public MovePlayerEvent(long playerId, Direction direction) {
		this(playerId, direction, 1);
	}

	public MovePlayerEvent(long playerId, Direction direction, int range) {
		super(playerId);
		_direction = direction;
		_range = range;
	}

	public void doEvent(Arena arena) {
		_arena = arena;
		_player = getPlayer(arena);

		Pitch pitch = arena.getPitch();
		pitch.movePlayer(_player, _direction, _range);
	}

	public void undoEvent() {
		Pitch pitch = _arena.getPitch();
		pitch.movePlayer(_player, _direction.inverse(), _range);
	}

	@Override
	public String getString() {
		return Concat.buildLog(getClass(),
				new Pair("playerId", getPlayerId()),
				new Pair("direction", _direction),
				new Pair("range", _range));
	}

	public Direction getDirection() {
		return _direction;
	}

}
