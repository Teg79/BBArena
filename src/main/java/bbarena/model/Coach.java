package bbarena.model;

import bbarena.model.team.Team;
import bbarena.model.util.Concat;
import bbarena.model.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Coach {

	private static final Logger log = LoggerFactory.getLogger(Coach.class);
	private String _name;
	private Team _team;
    private ChoiceFilter _filter = choices -> choices;

    public Coach(String name, Team team) {
		_name = name;
		_team = team;
	}

	public String getName() {
		return _name;
	}

	public Team getTeam() {
		return _team;
	}

	public Choice choice(String question, Choice[] choicesArray, Choice... additionalChoices) {
		LinkedHashSet choices = new LinkedHashSet(Arrays.asList(choicesArray));
		for (Choice choice : additionalChoices) {
			choices.add(choice);
		}
		return choice(question, choices);
	}

	public Choice choice(String question, Choice[]... choiceGroups) {
		LinkedHashSet choices = new LinkedHashSet();
		for (Choice[] choicesArray : choiceGroups) {
			choices.addAll(Arrays.asList(choicesArray));
		}
		return choice(question, choices);
	}

	public Choice choice(String question, Choice... choices) {
		return choice(question, new LinkedHashSet<>(Arrays.asList(choices)));
	}

	public <C extends Choice> C pick(String question, Set<C> choices) {
		return (C) choice(question, new LinkedHashSet<>(choices));
	}

	public Choice choice(String question, Set<Choice> choices) {

        choices = _filter.filter(choices);

		Choice res;
		do {
			res = ask(question, choices);

			if (!choices.contains(res)) {
				log.warn("Choice " + res + " not valid, valid values are: " + choices.toString());
			}
		} while (!choices.contains(res));

		log.info(Concat.buildLog(getClass(),
				new Pair("coach", _name),
				new Pair("question", question),
				new Pair("answer", res)));
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
