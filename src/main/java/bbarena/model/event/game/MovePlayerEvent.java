package bbarena.model.event.game;

import bbarena.model.Arena;
import bbarena.model.Direction;
import bbarena.model.pitch.Pitch;
import bbarena.model.team.Player;
import bbarena.model.util.Concat;
import bbarena.model.util.Pair;

public class MovePlayerEvent extends PlayerEvent {

	private static final long serialVersionUID = 2911617554463404375L;

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
		Player player = getPlayer(arena);

		Pitch pitch = arena.getPitch();
		pitch.movePlayer(player, _direction, _range);
	}

	public void undoEvent(Arena arena) {
		Pitch pitch = arena.getPitch();
		Player player = getPlayer(arena);
		pitch.movePlayer(player, _direction.inverse(), _range);
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
