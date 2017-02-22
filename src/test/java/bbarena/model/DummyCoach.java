package bbarena.model;

import bbarena.model.team.Team;

import java.util.Set;

public class DummyCoach extends Coach {

    public DummyCoach(String name, Team team) {
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
