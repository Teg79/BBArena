package net.sf.bbarena.model;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import net.sf.bbarena.model.team.Team;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Coach {

	private static final Logger log = LoggerFactory.getLogger(Coach.class);
	private Team _team;
    private ChoiceFilter _filter = choices -> choices;

    public Coach(Team team) {
		_team = team;
	}
	
	public Team getTeam() {
		return _team;
	}
	
	public Choice choice(String question, Set<Choice> choices) {

        choices = _filter.filter(choices);

		Choice res = ask(question, choices);

		if (!choices.contains(res)) {
			throw new IllegalArgumentException("Choice " + res + " not valid, valid values are: " + choices.toString());
		}

		StringBuilder msg = new StringBuilder();
		msg.append("choice: ")
			.append(question)
			.append(" [")
			.append(res)
			.append("]");
		log.info(msg.toString());
		return res;
	}

	public void notify(String message, Set<Choice> choices) {
		say(message, choices);
		StringBuilder msg = new StringBuilder();
		msg.append("notify: ")
			.append(message);
		log.info(msg.toString());
	}
	
	protected abstract Choice ask(String question, Set<Choice> choices);
	
	protected abstract void say(String message, Set<Choice> choices);

    public void setChoiceFilter(ChoiceFilter choiceFilter) {
        if (choiceFilter != null) {
            _filter = choiceFilter;
        }
    }

}
