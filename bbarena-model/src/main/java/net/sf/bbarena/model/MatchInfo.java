package net.sf.bbarena.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MatchInfo implements Serializable {

	private static final long serialVersionUID = -4539363712532167223L;

	private String matchId = null;

	private Date start = null;

	private Date end = null;

	private Match.Status status = null;

	private List<ScoreBoard> scoreBoards = null;

	public MatchInfo() {
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Match.Status getStatus() {
		return status;
	}

	public void setStatus(Match.Status status) {
		this.status = status;
	}

	public List<ScoreBoard> getScoreBoards() {
		return scoreBoards;
	}

	public void setScoreBoards(List<ScoreBoard> scoreBoards) {
		this.scoreBoards = scoreBoards;
	}
}
