package net.sf.bbarena.rules.crap;

import net.sf.bbarena.model.*;
import net.sf.bbarena.model.team.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.Set;

public class SimCoach extends Coach {

    private static final Logger _log = LoggerFactory.getLogger(SimCoach.class);

    private final Random _random;
    private final String _name;
    private int _answer = 0;
    private Choice[] _answers;

    public SimCoach(String name, Team team, Choice... programmedChoices) {
        super(team);
        _random = new Random();
        _name = name;
        _answers = programmedChoices;
    }

    public String getName() {
        return _name;
    }

    @Override
    protected Choice ask(String question, Set<Choice> choices) {
        _log.info(_name + ": " + question + " " + choices.toString());
        Choice answer = _answer >= _answers.length ? null : _answers[_answer++];
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
