package bbarena.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.EOFException;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

@ServerEndpoint("/match/{match}/{coach}")
public class MatchEndpoint {

    private static final Logger _logger = LoggerFactory.getLogger(MatchEndpoint.class);
    public static Set<Session> SESSIONS = new LinkedHashSet<>();

    @OnMessage
    public String onMessage(String message, Session session, @PathParam("match") String match, @PathParam("coach") String coach) {
        _logger.info("Message for match " + match + " coach " + coach + ": " + message);
        // Delivery message to Arena engine

        try {
            session.getBasicRemote().sendText("ciao");
            session.getBasicRemote().sendText("ciao1");
            session.getBasicRemote().sendText("ciao2");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("match") String match, @PathParam("coach") String coach) {
        _logger.info("Open session for match " + match + " coach " + coach);
        // Add session
        SESSIONS.add(session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("match") String match, @PathParam("coach") String coach) {
        _logger.info("Close session for match " + match + " coach " + coach);
        // Close session
        SESSIONS.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable, @PathParam("match") String match, @PathParam("coach") String coach) {
        if (throwable instanceof EOFException) {
            _logger.info("Connection lost on session for match " + match + " coach " + coach);
        } else {
            _logger.info("Error on session for match " + match + " coach " + coach, throwable);
        }
    }
}
