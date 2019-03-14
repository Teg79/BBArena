package bbarena.server;

import bbarena.model.Coach;
import bbarena.model.Match;
import bbarena.model.Roll;
import bbarena.model.dice.DieRandomizer;
import bbarena.rules.crap.Crap;

/**
 * Created by Teg on 10/12/2016.
 */
public class MatchServer {

    public void start(Coach home, Coach away, DieRandomizer roller, SessionEventListener sessionEventListener) {

        Match<Crap> match = new Match<>(home, away);

        Roll.Companion.setGenerator(roller);

        match.getEventManager().addListener(sessionEventListener);

        match.start(new Crap());
    }

}
