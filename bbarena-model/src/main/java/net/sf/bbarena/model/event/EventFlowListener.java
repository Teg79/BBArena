package net.sf.bbarena.model.event;

/**
 * Implementations of this interface will be notified of EventLine changes
 *
 * @author f.bellentani
 */
public interface EventFlowListener {

	public void beforeDoEvent(Event e);

	public void afterDoEvent(Event e);

	public void beforeUndoEvent(Event e);

	public void afterUndoEvent(Event e);

	public void eventSizeChanged(int newSize);

}
