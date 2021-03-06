package bbarena.model.event.game;

import bbarena.model.Arena;
import bbarena.model.Coordinate;
import bbarena.model.pitch.Dugout;
import bbarena.model.pitch.Dugout.DugoutRoom;
import bbarena.model.pitch.Pitch;
import bbarena.model.pitch.Square;
import bbarena.model.team.Player;
import bbarena.model.util.Concat;
import bbarena.model.util.Pair;

public class PutPlayerInDugoutEvent extends PlayerEvent {

	private static final long serialVersionUID = -3430391401528941474L;

	private Integer _coach = null;
	private Player _player = null;
	private String _dugoutRoom = null;
	private Coordinate _fromPitch = null;
	private DugoutRoom _fromDugout = null;

public PutPlayerInDugoutEvent(long playerId, String dugoutRoom) {
		super(playerId);
		_dugoutRoom = dugoutRoom;
	}

	public void doEvent(Arena arena) {
		_player = getPlayer(arena);
		_coach = arena.getTeams().indexOf(_player.getTeam());
		DugoutRoom to = arena.getPitch().getDugout(_player.getTeam()).getRoom(_dugoutRoom);

		Pitch pitch = arena.getPitch();
		if (_player.isInDugout()) {
			Dugout dugout = pitch.getDugout(_player.getTeam());
			_fromDugout = dugout.getRoom(_player);
			_fromPitch = null;
		} else {
			Square square = _player.getSquare();
			_fromPitch = square.getCoords();
			_fromDugout = null;
		}
		pitch.putPlayer(_player, to);
	}

	public void undoEvent(Arena arena) {
		Pitch pitch = arena.getPitch();
		if (_fromPitch != null) {
			pitch.putPlayer(_player, _fromPitch);
		} else {
			pitch.putPlayer(_player, _fromDugout);
		}
	}

	@Override
	public String getString() {
		return Concat.buildLog(getClass(),
				new Pair("playerId", getPlayerId()),
				new Pair("dugoutRoom", _dugoutRoom));
	}

	public String getDugoutRoom() {
		return _dugoutRoom;
	}

}
