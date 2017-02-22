package bbarena.server;

import bbarena.server.json.ArenaConverter;
import bbarena.server.json.Envelope;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import bbarena.model.Match;
import bbarena.model.event.Event;
import bbarena.model.event.EventFlowListener;
import bbarena.model.event.EventManager;
import bbarena.model.event.game.MatchStatusChangeEvent;
import bbarena.model.team.Roster;
import bbarena.model.team.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by Teg on 10/12/2016.
 */
public class SessionEventListener implements EventFlowListener {

    private static final Logger _log = LoggerFactory.getLogger(SessionEventListener.class);
    private Session _session;

    public SessionEventListener(Session session) {
        _session = session;
    }

    @Override
    public void beforeDoEvent(Event e) {

    }

    @Override
    public void afterDoEvent(Event e) {
        try {
            XStream xStream = new XStream(new JsonHierarchicalStreamDriver());
            xStream.registerConverter(new ArenaConverter());
            xStream.setMode(XStream.NO_REFERENCES);
            xStream.omitField(Team.class, "players");
            xStream.omitField(Roster.class, "players");
            String json = xStream.toXML(new Envelope(e));
            _session.getBasicRemote().sendText(json);
        } catch (IOException e1) {
            _log.warn("Error sending message to " + _session.getQueryString());
        }
        if (e instanceof MatchStatusChangeEvent) {
            MatchStatusChangeEvent matchStatusChangeEvent = (MatchStatusChangeEvent) e;
            if (matchStatusChangeEvent.getMatchStatus() == Match.Status.FINISHED) {
                try {
                    _session.close();
                } catch (IOException e1) {
                    _log.warn("Error closing session " + _session.getQueryString());
                }
            }
        }
    }

    @Override
    public void beforeUndoEvent(Event e) {

    }

    @Override
    public void afterUndoEvent(Event e) {
        try {
            _session.getBasicRemote().sendText(e.getString());
        } catch (IOException e1) {
            _log.warn("Error sending message to " + _session.getQueryString());
        }
    }

    @Override
    public void eventSizeChanged(int newSize) {

    }

    @Override
    public void setEventManager(EventManager eventManager) {

    }
}
