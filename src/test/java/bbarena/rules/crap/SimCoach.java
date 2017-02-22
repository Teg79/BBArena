package bbarena.rules.crap;

import bbarena.model.*;
import bbarena.model.team.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SimCoach extends Coach {

    private static final Logger _log = LoggerFactory.getLogger(SimCoach.class);

    private final Random _random;
    private final String _name;
    private int _answer = 0;
    private List<Choice> _answers;

    public SimCoach(String name, Team team, Choice... programmedChoices) {
        super(name, team);
        _random = new Random();
        _name = name;
        _answers = new ArrayList<>(Arrays.asList(programmedChoices));
    }

    public void addAnswer(Choice choice) {
        _answers.add(choice);
    }

    @Override
    protected Choice ask(String question, Set<Choice> choices) {
        _log.info(_name + ": " + question + " " + choices.toString());
        Choice answer = _answer >= _answers.size() ? null : _answers.get(_answer++);
        if (answer == null) {
            answer = randomAnswer(choices);
        }
        _log.info("Answer: " + answer);

        return answer;
    }

    private Choice randomAnswer(Set<Choice> choices) {
        Choice[] array = choices.toArray(new Choice[choices.size()]);
        int pos = _random.nextInt(array.length);
        return array[pos];
    }

    @Override
    protected void say(String message, Set<Choice> choices) {
        _log.info(_name + ": " + message + " " + choices.toString());
    }

}
