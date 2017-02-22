package bbarena.model.event;

/**
 * Implementations of this interface will be notified of EventLine changes
 *
 * @author f.bellentani
 */
public abstract class BaseEventFlowListener implements EventFlowListener {

    private EventManager _eventManager;

    @Override
    public void setEventManager(EventManager eventManager) {
        _eventManager = eventManager;
    }

    public EventManager getEventManager() {
        return _eventManager;
    }
}
