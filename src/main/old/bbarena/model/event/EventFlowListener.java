package bbarena.model.event;

/**
 * Implementations of this interface will be notified of EventLine changes
 *
 * @author f.bellentani
 */
public interface EventFlowListener {

	void beforeDoEvent(Event e);

	void afterDoEvent(Event e);

	void beforeUndoEvent(Event e);

	void afterUndoEvent(Event e);

	void eventSizeChanged(int newSize);

    void setEventManager(EventManager eventManager);

}
