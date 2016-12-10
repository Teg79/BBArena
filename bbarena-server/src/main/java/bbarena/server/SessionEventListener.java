package bbarena.server;

import net.sf.bbarena.model.event.Event;
import net.sf.bbarena.model.event.EventFlowListener;
import net.sf.bbarena.model.event.EventManager;
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
            _session.getBasicRemote().sendText(e.getString());
        } catch (IOException e1) {
            _log.warn("Error sending message to " + _session.getQueryString());
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