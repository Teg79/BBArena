package net.sf.bbarena.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import net.sf.bbarena.model.team.Team;

public class Referee extends Observable implements Serializable {

	private static final long serialVersionUID = 5946955340981726094L;

	public enum RefereeStatus {
		NORMAL, BONDED, WATCHING;
	}

	private Map<Team, RefereeStatus> teamStatus = new HashMap<Team, RefereeStatus>();

	protected Referee(Team... teams) {
		for(Team team : teams) {
			teamStatus.put(team, RefereeStatus.NORMAL);
		}
	}

	public void setStatus(Team team, RefereeStatus status) {
		teamStatus.put(team, status);
		setChanged();
		notifyObservers();
	}

	public RefereeStatus getStatus(Team team) {
		return teamStatus.get(team);
	}

}
