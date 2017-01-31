package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.Coordinate;
import net.sf.bbarena.model.pitch.Dugout;
import net.sf.bbarena.model.pitch.Dugout.DugoutRoom;
import net.sf.bbarena.model.pitch.Pitch;
import net.sf.bbarena.model.team.Player;
import net.sf.bbarena.model.util.Concat;
import net.sf.bbarena.model.util.Pair;

public class PutPlayerInPitchEvent extends PlayerEvent {

	private static final long serialVersionUID = 4021761692648543307L;

	private Player _player = null;
	private Coordinate _to = null;
	private DugoutRoom _fromDugout = null;
	private Coordinate _fromSquare = null;

	public PutPlayerInPitchEvent(long playerId, int toX, int toY) {
		super(playerId);
		_to = new Coordinate(toX, toY);
	}

	public void doEvent(Arena arena) {
		_player = getPlayer(arena);
		Pitch pitch = arena.getPitch();

		if (_player.isInDugout()) {
			Dugout dugout = pitch.getDugout(_player.getTeam());
			_fromDugout = dugout.getRoom(_player);
		} else if (_player.getSquare() != null){
			_fromSquare = _player.getSquare().getCoords();
		}
		pitch.putPlayer(_player, _to);
	}

	public void undoEvent(Arena arena) {
		Pitch pitch = arena.getPitch();

		if(_fromDugout != null) {
			pitch.putPlayer(_player, _fromDugout);
		} else if(_fromSquare != null) {
			pitch.putPlayer(_player, _fromSquare);
		}
	}

	@Override
	public String getString() {
		return Concat.buildLog(getClass(),
				new Pair("playerId", getPlayerId()),
				new Pair("to", _to));
	}

	public Coordinate getDestination() {
		return _to;
	}

}
