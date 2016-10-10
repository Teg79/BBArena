package net.sf.bbarena.model;

import java.util.Arrays;
import java.util.List;

import net.sf.bbarena.model.team.Team;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Coach<C extends Choice> {

	private static final Logger log = LoggerFactory.getLogger(Coach.class);
	private List<Team> _teams;
	
	public Coach(Team... teams) {
		_teams = Arrays.asList(teams);
	}
	
	public List<Team> getTeams() {
		return _teams;
	}
	
	public C choice(String question, C... choices) {
		C res = ask(question, choices);
		StringBuilder msg = new StringBuilder();
		msg.append("choice: ")
			.append(question)
			.append(" [")
			.append(res)
			.append("]");
		log.info(msg.toString());
		return res;
	}
	
	public void notify(String message, C... choices) {
		say(message, choices);
		StringBuilder msg = new StringBuilder();
		msg.append("notify: ")
			.append(message);
		log.info(msg.toString());
	}
	
	protected abstract C ask(String question, C... choices);
	
	protected abstract void say(String message, C... choices);
	
}
