package bbarena.server;

import bbarena.model.Coordinate;
import bbarena.model.choice.Continue;
import bbarena.model.choice.FlipRoll;
import bbarena.model.dice.DieRandomizer;
import bbarena.model.pitch.Square;
import bbarena.model.team.Player;
import bbarena.model.team.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.EOFException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/match/{match}/{coach}")
public class MatchEndpoint {

    private static final Logger _logger = LoggerFactory.getLogger(MatchEndpoint.class);
    public static Map<Session, MatchServer> _servers = new ConcurrentHashMap<>();

    @OnMessage
    public String onMessage(String message, Session session, @PathParam("match") String match, @PathParam("coach") String coach) {
        _logger.info("Message for match " + match + " coach " + coach + ": " + message);
        // Delivery message to Arena engine

        MatchServer matchServer = _servers.get(session);

        Team t1 = Factory.buildOrcTeam();
        Team t2 = Factory.buildChaosTeam();

        Iterator<Player> p1Iterator = t1.getPlayers().iterator();
        RemoteCoach c1 = new RemoteCoach(session, "C1", t1,
                FlipRoll.KICK,
                p1Iterator.next(),
                new Square(new Coordinate(12, 6)),
                p1Iterator.next(),
                new Square(new Coordinate(12, 7)),
                p1Iterator.next(),
                new Square(new Coordinate(12, 8)),
                p1Iterator.next(),
                new Square(new Coordinate(10, 2)),
                p1Iterator.next(),
                new Square(new Coordinate(10, 5)),
                p1Iterator.next(),
                new Square(new Coordinate(10, 9)),
                p1Iterator.next(),
                new Square(new Coordinate(10, 12)),
                p1Iterator.next(),
                new Square(new Coordinate(9, 1)),
                p1Iterator.next(),
                new Square(new Coordinate(9, 4)),
                p1Iterator.next(),
                new Square(new Coordinate(9, 10)),
                p1Iterator.next(),
                new Square(new Coordinate(9, 13)),
                new Continue(),
                new Square(new Coordinate(15, 2)));
        Iterator<Player> p2Iterator = t2.getPlayers().iterator();
        RemoteCoach c2 = new RemoteCoach(session, "C2", t2,
                p2Iterator.next(),
                new Square(new Coordinate(13, 6)),
                p2Iterator.next(),
                new Square(new Coordinate(13, 7)),
                p2Iterator.next(),
                new Square(new Coordinate(13, 8)),
                p2Iterator.next(),
                new Square(new Coordinate(15, 2)),
                p2Iterator.next(),
                new Square(new Coordinate(15, 5)),
                p2Iterator.next(),
                new Square(new Coordinate(15, 9)),
                p2Iterator.next(),
                new Square(new Coordinate(15, 12)),
                p2Iterator.next(),
                new Square(new Coordinate(16, 1)),
                p2Iterator.next(),
                new Square(new Coordinate(16, 4)),
                p2Iterator.next(),
                new Square(new Coordinate(16, 10)),
                p2Iterator.next(),
                new Square(new Coordinate(16, 13)),
                new Continue());

        DieRandomizer roller = new SimGenerator(
                null, null, // Weather
                null, null, // FAME c1
                null, null, // FAME c2
                1, // Flip Coin, c1 wins
                8, 3, // Bounce W for 8
                4, 3, // Kick Off Event
                5, // Fail catch
                6, // Scatter
                5, // Catch
                5 // Scatter
        );
        matchServer.start(c1, c2, roller, new SessionEventListener(session));

        return "Started";
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("match") String match, @PathParam("coach") String coach) {
        _logger.info("Open session for match " + match + " coach " + coach);
        // Add session

        MatchServer matchServer = new MatchServer();
        _servers.put(session, matchServer);

    }

    @OnClose
    public void onClose(Session session, @PathParam("match") String match, @PathParam("coach") String coach) {
        _logger.info("Close session for match " + match + " coach " + coach);
        // Close session
        _servers.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable, @PathParam("match") String match, @PathParam("coach") String coach) {
        if (throwable instanceof EOFException) {
            _logger.info("Connection lost on session for match " + match + " coach " + coach);
        } else {
            _logger.info("Error on session for match " + match + " coach " + coach + ": " + throwable.getMessage());
        }
    }
}
