package net.sf.bbarena.model;

import java.io.Serializable;
import java.util.Date;

import net.sf.bbarena.model.Arena.MatchStatus;

public class MatchInfo implements Serializable {

	private static final long serialVersionUID = -4539363712532167223L;

	private String matchId = null;

	private Date start = null;

	private Date end = null;

	private MatchStatus status = null;

	private Score score = null;

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

	public MatchStatus getStatus() {
		return status;
	}

	public void setStatus(MatchStatus status) {
		this.status = status;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

}
