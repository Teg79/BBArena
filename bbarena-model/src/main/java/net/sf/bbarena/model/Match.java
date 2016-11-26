package net.sf.bbarena.model;

import net.sf.bbarena.model.event.EventManager;
import net.sf.bbarena.model.team.Team;

import java.util.*;

public class Match<R extends RuleSet> {

	public enum Status {
		STARTING, PLAYING, ENDING, FINISHED;
	}

	private static final Map<String, Match> _matches = new HashMap<>();

	public static Match getMatch(Long matchId) {
		return _matches.get(matchId);
	}

	public static Set<String> getMatchIds() {
		return _matches.keySet();
	}

	private String _matchId = null;
	private Arena _arena;
	private EventManager _eventManager;
	private R _source;
	private List<Coach> _coaches;

    private Integer _currentDrive = 1;

	private Status _status = null;
	private Date _start = null;
	private Date _end = null;
	
	public Match(Coach... coaches) {
		this(null, coaches);
	}
		
	public Match(String pitchType, Coach... coaches) {
		_matchId = UUID.randomUUID().toString();
		_coaches = Arrays.asList(coaches);
		List<Team> teams = new ArrayList<Team>();
		for (int i = 0; i < coaches.length; i++) {
			teams.add(coaches[i].getTeam());
		}
		_arena = new Arena(this, pitchType, teams.toArray(new Team[teams.size()]));
		_eventManager = new EventManager(_arena);
		_matches.put(_matchId, this);
	}

	public void start(R source) {
		_source = source;
		_source.start(_eventManager, _coaches);
	}

	public Arena getArena() {
		return _arena;
	}

	public EventManager getEventManager() {
		return _eventManager;
	}

	public R getSource() {
		return _source;
	}
	
	public List<Coach> getCoaches() {
		return _coaches;
	}

    public Integer getCurrentDrive() {
        return _currentDrive;
    }

    public void setCurrentDrive(Integer currentDrive) {
        _currentDrive = currentDrive;
    }

    public Status getStatus() {
        return _status;
    }

    public void setStatus(Status status) {
        _status = status;
    }

    public Date getStart() {
        return _start;
    }

    public void setStart(Date start) {
        _start = start;
    }

    public Date getEnd() {
        return _end;
    }

    public void setEnd(Date end) {
        _end = end;
    }

    public MatchInfo getMatchInfo() {
		MatchInfo res = new MatchInfo();

		res.setEnd(_end);
		res.setMatchId(_matchId);
		res.setStart(_start);
		res.setStatus(_status);
        res.setScoreBoards(_arena.getScoreBoards());

		return res;
	}

}
