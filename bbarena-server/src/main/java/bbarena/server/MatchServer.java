package bbarena.server;

import net.sf.bbarena.model.Coach;
import net.sf.bbarena.model.Match;
import net.sf.bbarena.model.Roll;
import net.sf.bbarena.model.dice.DieRandomizer;
import net.sf.bbarena.rules.crap.Crap;

/**
 * Created by Teg on 10/12/2016.
 */
public class MatchServer {

    public void start(Coach home, Coach away, DieRandomizer roller, SessionEventListener sessionEventListener) {

        Match<Crap> match = new Match<>(home, away);

        Roll.setGenerator(roller);

        match.getEventManager().addListener(sessionEventListener);

        match.start(new Crap());
    }

}
