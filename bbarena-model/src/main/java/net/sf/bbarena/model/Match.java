package net.sf.bbarena.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.bbarena.model.event.EventManager;
import net.sf.bbarena.model.team.Team;

public class Match<R extends RuleSet> {

	private Arena _arena;
	private EventManager _eventManager;
	private R _source;
	private List<Coach> _coaches;
	
	public Match(Coach... coaches) {
		this(null, coaches);
	}
		
	public Match(String pitchType, Coach... coaches) {
		_coaches = Arrays.asList(coaches);
		List<Team> teams = new ArrayList<Team>();
		for (int i = 0; i < coaches.length; i++) {
			teams.add(coaches[i].getTeam());
		}
		_arena = new Arena(pitchType, teams.toArray(new Team[teams.size()]));
		_eventManager = new EventManager(_arena);
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

}
