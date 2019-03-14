package bbarena.server.json;

import bbarena.model.event.Event;

/**
 * Created by teg on 26/12/16.
 */
public class Envelope {

    private String _type;
    private Event _event;

    public Envelope() {
    }

    public Envelope(Event event) {
        _event = event;
        _type = _event.getClass().getSimpleName();
    }

    public Event getEvent() {
        return _event;
    }

    public void setEvent(Event event) {
        _event = event;
    }
}
