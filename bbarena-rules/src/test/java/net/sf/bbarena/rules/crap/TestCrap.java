package net.sf.bbarena.rules.crap;

import net.sf.bbarena.model.Match;
import net.sf.bbarena.model.team.Team;
import org.junit.Test;

public class TestCrap {

    @Test
    public void testMatch() {
        Team t1 = Factory.buildTeam();
        Team t2 = Factory.buildTeam();

        SimCoach c1 = new SimCoach("C1", t1);
        SimCoach c2 = new SimCoach("C2", t2);

        Match<Crap> match = new Match<>(c1, c2);
        match.start(new Crap());

    }

}
