package net.sf.bbarena.view;

import net.sf.bbarena.model.Choice;
import net.sf.bbarena.model.Coach;
import net.sf.bbarena.model.team.Team;

import java.util.Set;

public class CoachUi extends Coach {

    public CoachUi(String name, Team team) {
        super(name, team);
    }

    @Override
    protected Choice ask(String question, Set<Choice> choices) {
        return null;
    }

    @Override
    protected void say(String message, Set<Choice> choices) {

    }
}
