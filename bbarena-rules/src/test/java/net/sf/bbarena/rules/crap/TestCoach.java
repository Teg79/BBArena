package net.sf.bbarena.rules.crap;

import junit.framework.Assert;
import net.sf.bbarena.model.Choice;
import net.sf.bbarena.model.Coach;
import net.sf.bbarena.model.Direction;
import net.sf.bbarena.model.RangeRuler;
import net.sf.bbarena.model.team.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TestCoach {

    private static final Logger _log = LoggerFactory.getLogger(TestCoach.class);

    private Choice[] _answers = new Choice[]{
            Direction.N, Direction.NW, RangeRuler.Range.QUICK
    };

    @Test
    public void test() {
        Team team = Factory.buildTeam();

        SimCoach coach = new SimCoach("Coach A", team, _answers);
        Choice choice = coach.ask("Direction", new LinkedHashSet<>(Arrays.asList(Direction.values())));
        Assert.assertEquals(Direction.N, choice);
        choice = coach.ask("Direction", new LinkedHashSet<>(Arrays.asList(Direction.values())));
        Assert.assertEquals(Direction.NW, choice);
        choice = coach.ask("Pass", new LinkedHashSet<>(Arrays.asList(RangeRuler.Range.values())));
        Assert.assertEquals(RangeRuler.Range.QUICK, choice);
    }

}
