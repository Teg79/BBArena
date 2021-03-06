package bbarena.model.event;

import bbarena.model.Arena;
import bbarena.model.RollResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Event implements Serializable {

	/*
		MoveBall
		AddBlockTarget
		ClearBlockTargets
		SetBomb (sets bomb on field square)
		AddPlayerToField
		SetHalf
		AddLogRow
		AddPath (moved path shown as numbers, adds one number to current path)
		ClearPath (clears whole path)
		SetActivePlayer
		SetBallCarrier
		SetBallInGame (sets ball's inGame value to true, i.e. ball will be shown)
		SetBlocker (sets blocker)
		SetBlockResult (sets block result)
		SetKickoffResult (sets kickoff result)
		SetPlayerAction (sets current players action i.e. move, block, blitz, etc.)
		SpellSet (sets active spell)
		SetMatchState (sets matches global state, if I only could remember what those were)
		ChangeWeather (sets current weather)
		PlayerMove
		PlayerSetInjuryText
		PlayerSetStatus
		TeamApothecarySet
		MovePlayerToDugout
		TeamSetRerolls
		TeamSetScore
		TeamSetSpecials (get the ref and who the referee is watching at)
		TeamSetTurn
		TeamWizardSet
	*/

	private static final long serialVersionUID = -9143193751782902257L;

	private int _id = 0;
	private List<RollResult> _diceRolls = new ArrayList<>();
	private boolean _executed = false;

	protected void setId(int id) {
		_id  = id;
	}

	public int getId() {
		return _id;
	}

	public List<RollResult> getDiceRolls() {
		return _diceRolls;
	}

	public void addDiceRoll(RollResult diceRoll) {
		_diceRolls.add(diceRoll);
	}

	protected abstract void doEvent(Arena arena);

    protected abstract void undoEvent(Arena arena);

	public boolean isExecuted() {
		return _executed;
	}

	protected void setExecuted(boolean executed) {
		_executed = executed;
	}

	public abstract String getString();

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("[").append(_id).append("] ");
		res.append(getString()).append(" ").append(_diceRolls.toString());
		return res.toString();
	}
}
