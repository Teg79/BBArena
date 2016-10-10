package net.sf.bbarena.model;

import java.io.Serializable;
import net.sf.bbarena.model.team.TeamInfo;

public class MatchTeamInfo extends TeamInfo implements Serializable {

	private static final long serialVersionUID = -5647762138958951271L;

	private int score = 0;

	public MatchTeamInfo(TeamInfo team) {
		this(team, 0);
	}

	public MatchTeamInfo(TeamInfo team, int score) {
		super(team.getId(), team.getName(), team.getCoach(), team.getRace(),
				team.getTeamValue());

		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void addTD() {
		this.score++;
	}

	public void addTD(int tds) {
		for(int i = 0; i < tds; i++) {
			addTD();
		}
	}

}