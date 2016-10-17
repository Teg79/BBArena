package net.sf.bbarena.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import net.sf.bbarena.model.team.Team;
import net.sf.bbarena.model.team.TeamInfo;

@Deprecated
public class Score extends Observable implements Serializable {

	private static final long serialVersionUID = -1511601646698905758L;

	private List<MatchTeamInfo> scores = new ArrayList<MatchTeamInfo>();

	protected Score(Team[] teams) {
		super();

		for (Team team : teams) {
			MatchTeamInfo mti = new MatchTeamInfo(team.getTeamInfo());
			scores.add(mti);
		}
	}

	public Score(TeamInfo[] teams) {
		super();

		for (TeamInfo team : teams) {
			MatchTeamInfo mti = new MatchTeamInfo(team);
			scores.add(mti);
		}
	}

	public List<MatchTeamInfo> getScores() {
		return scores;
	}

	/**
	 * Returns the score of the Team
	 *
	 * @param team
	 *            Team
	 * @return The Team's score, -1 if Team doesn't exists in Match
	 */
	public int getScore(Team team) {
		int res = -1;

		MatchTeamInfo mti = null;
		mti = getTeam(team);
		if (mti != null) {
			res = mti.getScore();
		}

		return res;
	}

	private MatchTeamInfo getTeam(Team team) {
		MatchTeamInfo res = null;
		long id = team.getId();

		for (MatchTeamInfo mti : scores) {
			if (mti.getId() == id) {
				res = mti;
				break;
			}
		}
		return res;
	}

	public void addScore(Team team) {
		addScore(team, 1);
	}

	public void addScore(Team team, int score) {
		MatchTeamInfo mti = getTeam(team);
		if (mti != null) {
			mti.addTD(score);
			setChanged();
		}
		notifyObservers();
	}

}
