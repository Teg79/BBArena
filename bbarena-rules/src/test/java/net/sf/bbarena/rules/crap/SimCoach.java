package net.sf.bbarena.rules.crap;

import net.sf.bbarena.model.Choice;
import net.sf.bbarena.model.Coach;
import net.sf.bbarena.model.Direction;
import net.sf.bbarena.model.RangeRuler;
import net.sf.bbarena.model.team.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class SimCoach extends Coach {

    private static final Logger _log = LoggerFactory.getLogger(SimCoach.class);

    private final String _name;
    private int _answer = 0;
    private Choice[] _answers = new Choice[]{
            Direction.N, Direction.NW, RangeRuler.Range.QUICK
    };

    public SimCoach(String name, Team team, Choice... programmedChoices) {
        super(team);
        _name = name;
    }

    public String getName() {
        return _name;
    }

    @Override
    protected Choice ask(String question, Set<Choice> choices) {
        _log.info(_name + ": " + question + " " + choices.toString());
        Choice answer = _answer >= _answers.length ? null : _answers[_answer++];
        _log.info("Answer: " + answer);

        return answer;
    }

    @Override
    protected void say(String message, Set<Choice> choices) {
        _log.info(_name + ": " + message + " " + choices.toString());
    }

}
