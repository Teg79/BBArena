package net.sf.bbarena.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import net.sf.bbarena.model.exception.TurnException;
import net.sf.bbarena.model.team.Team;

/**
 * The TurnMarker implementation is where to store all the info about turns,
 * rerolls and times. A game is split in times (first half, second half, over
 * time for example), a time is split in turns and in a turn every team has a
 * status and can play only once.
 *
 * @author f.bellentani
 */
public class TurnMarker extends Observable implements Serializable {

	private static final long serialVersionUID = -8322832374823269100L;

	/**
	 * TurnStatus is the Team status for the current turn.
	 *
	 * @author f.bellentani
	 */
	public enum TurnStatus {
		TO_PLAY, PLAYING, DONE
	}

	private Team _team = null;

	private int _turn = 0;

	private int _time = 0;

	private int _rerolls = 0;

	private boolean _usedReroll = false;

	private TurnStatus _status = TurnStatus.DONE;

	/**
	 * TeamMarker stores the turn status for one Team
	 *
	 * @author f.bellentani
	 */
//	public static class TeamMarker extends Observable implements Serializable {
//
//		private static final long serialVersionUID = 7683003586710333997L;
//
//
//		public TeamMarker(Team team) {
//			this.team = team;
//		}
//
//		/**
//		 * Sets the marker for a new game time.
//		 *
//		 */
//		private void newTime() {
//			newTurn();
//			this.rerolls = team.getReRolls();
//			this.turn = 0;
//			this.time++;
//			this.setChanged();
//		}
//
//		/**
//		 * Sets the marker for a new game turn.
//		 *
//		 */
//		private void newTurn() {
//			if (this.status != TurnStatus.DONE) {
//				throw new TurnException("Unable to activate the team "
//						+ team.getName() + " for a new turn. The status is "
//						+ status + " but status " + TurnStatus.DONE
//						+ " is required!");
//			}
//			this.status = TurnStatus.TO_PLAY;
//			this.usedReroll = false;
//			this.setChanged();
//		}
//
//		/**
//		 * Starts the Team turn.
//		 *
//		 */
//		public void startTurn() {
//			if (this.status != TurnStatus.TO_PLAY) {
//				throw new TurnException("Unable to start the turn for team "
//						+ team.getName() + ". The status is " + status
//						+ " but status " + TurnStatus.TO_PLAY + " is required!");
//			}
//			this.turn++;
//			this.status = TurnStatus.PLAYING;
//			this.usedReroll = false;
//			this.setChanged();
//			this.notifyObservers();
//		}
//
//		/**
//		 * Ends the Team turn.
//		 *
//		 */
//		public void finishTurn() {
//			if (this.status != TurnStatus.PLAYING) {
//				throw new TurnException("Unable to finish the turn for team "
//						+ team.getName() + ". The status is " + status
//						+ " but status " + TurnStatus.PLAYING + " is required!");
//			}
//			this.status = TurnStatus.DONE;
//			this.usedReroll = false;
//			this.setChanged();
//			this.notifyObservers();
//		}
//
//		/**
//		 * Moves the turn marker
//		 *
//		 * @param turns
//		 *            Number of turn to move, the value can be negative to move
//		 *            turns backwards.
//		 */
//		private void moveTurnMarker(int turns) {
//			this.rerolls += turns;
//			this.setChanged();
//		}
//
//		/**
//		 * Adds a single Reroll to the Team.
//		 *
//		 */
//		public void addReroll() {
//			this.rerolls++;
//			this.setChanged();
//			this.notifyObservers();
//		}
//
//		/**
//		 * Substract a Reroll from the Team and sets that a Reroll is used.
//		 *
//		 */
//		public void useReroll() {
//			removeReroll();
//			this.usedReroll = true;
//			this.setChanged();
//			this.notifyObservers();
//		}
//
//		/**
//		 * Remove a Team Reroll
//		 *
//		 */
//		public void removeReroll() {
//			this.rerolls--;
//			this.setChanged();
//			this.notifyObservers();
//		}
//
//		public TurnStatus getStatus() {
//			return status;
//		}
//
//		public int getRerolls() {
//			return rerolls;
//		}
//
//		public Team getTeam() {
//			return team;
//		}
//
//		public int getTurn() {
//			return turn;
//		}
//
//		public boolean isUsedReroll() {
//			return usedReroll;
//		}
//
//		public int getTime() {
//			return this.time;
//		}
//
//		public TeamMarker clone() {
//			TeamMarker res = new TeamMarker(team);
//			res.rerolls = rerolls;
//			res.status = status;
//			res.time = time;
//			res.turn = turn;
//			res.usedReroll = usedReroll;
//			return res;
//		}
//
//		public void restore(TeamMarker tm) {
//			this.team = tm.team;
//			this.rerolls = tm.rerolls;
//			this.status = tm.status;
//			this.time = tm.time;
//			this.turn = tm.turn;
//			this.usedReroll = tm.usedReroll;
//
//			this.setChanged();
//			this.notifyObservers();
//		}
//
//	}

//	private int turn = 0;
//
//	private int time = 0;

	public TurnMarker(Team team) {
	    _team = team;
	}

	public int getTurn() {
		return _turn;
	}

	public void setTurn(int turn) {
		_turn = turn;
	}

	public int getTime() {
		return _time;
	}

	public void setTime(int time) {
		_time = time;
	}

	public int getRerolls() {
		return _rerolls;
	}

	public void setRerolls(int rerolls) {
		_rerolls = rerolls;
	}

	public boolean isUsedReroll() {
		return _usedReroll;
	}

	public void setUsedReroll(boolean usedReroll) {
		_usedReroll = usedReroll;
	}

	public TurnStatus getStatus() {
		return _status;
	}

	public void setStatus(TurnStatus status) {
		_status = status;
	}
/**
	 * Sets all the Team markers for a new game time.
	 *
	 */
//	public void newTime() {
//		for (TeamMarker marker : markers.values()) {
//			marker.newTime();
//		}
//
//		this.time++;
//		this.turn = 0;
//		setChanged();
//		notifyObservers();
//	}

	/**
	 * Sets all the Team markers for a new game turn.
	 *
	 */
//	public void newTurn() {
//		for (TeamMarker marker : markers.values()) {
//			marker.newTurn();
//		}
//
//		this.turn++;
//		setChanged();
//		notifyObservers();
//	}

	/**
	 * Moves the turn marker
	 *
	 * @param turns
	 *            Number of turn to move, the value can be negative to move
	 *            turns backwards.
	 */
//	public void moveTurnMarker(int turns) {
//		for (TeamMarker marker : markers.values()) {
//			marker.moveTurnMarker(turns);
//		}
//
//		this.turn += turns;
//		setChanged();
//		notifyObservers();
//	}

//	public TeamMarker getTeamMarker(Team team) {
//		TeamMarker res = markers.get(team);
//		return res;
//	}

//	public Collection<TeamMarker> getMarkers() {
//		return markers.values();
//	}
//
//	public TurnMarker clone() {
//		TurnMarker res = new TurnMarker();
//		res.time = time;
//		res.turn = turn;
//
//		for(Team team : markers.keySet()) {
//			TeamMarker tm = markers.get(team);
//			res.markers.put(team, tm.clone());
//		}
//
//		return res;
//	}

//	public void restore(TurnMarker tm) {
//		this.time = tm.time;
//		this.turn = tm.turn;
//
//		for(Team team : markers.keySet()) {
//			TeamMarker marker = markers.get(team);
//			TeamMarker newMarker = tm.markers.get(team);
//			if(newMarker != null) {
//				marker.restore(newMarker);
//			}
//		}
//
//		setChanged();
//		notifyObservers();
//	}

}
