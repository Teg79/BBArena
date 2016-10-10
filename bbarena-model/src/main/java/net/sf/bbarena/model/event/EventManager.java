package net.sf.bbarena.model.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.bbarena.model.Arena;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class controls the events flow in the model. Can be used to send event
 * to the model and undo the events.
 * 
 * Observers of this object will be notified when Events are added or removed
 * from the time line, and will be notified of the Event that is do() or
 * undone() with notifyObservers(Event e).
 * 
 * @author f.bellentani
 */
public class EventManager implements Serializable {

	private static final long serialVersionUID = -2236209545925666897L;

	protected Logger log = LoggerFactory.getLogger("arena.events");
	private List<EventFlowListener> _listeners = new ArrayList<EventFlowListener>();
	private Arena _arena;
	private List<Event> _events = new ArrayList<Event>();
	private int _current = 0;

	public EventManager(Arena arena) {
		super();
		_arena = arena;
	}

	/**
	 * Adds an event to the time line. The event will be executed when forward
	 * is called and the current position is the position before this event.
	 * Here the event id will be set to the previous size of the event list and
	 * the id will be returned.
	 * 
	 * @param event
	 *            The event to add to the line
	 * @return The Id assigned to the event
	 */
	public int putEvent(Event event) {
		int res = _events.size();
		event.setId(res);
		_events.add(event);

		listenersEventSizeChanged(_events.size());

		log.info("Added Event " + res);
		return res;
	}

	/**
	 * Removes an event from the time line. If the event is already executed
	 * then it will be undone before to remove it.
	 * 
	 */
	public void popEvent() {
		if (_current == _events.size() - 1) {
			backward();
		}

		_events = _events.subList(0, _events.size());

		listenersEventSizeChanged(_events.size());

		log.info("Removed last Event");
	}

	public int getCurrentPosition() {
		return _current;
	}

	public int getEventsSize() {
		return _events.size();
	}

	public Event getCurrentEvent() {
		return _events.get(_current);
	}

	/**
	 * Executes the next event in the list
	 * 
	 * @return The new current position
	 */
	public int forward() {
		int res = -1;
		if (_current < _events.size()) {
			Event e = _events.get(_current++);

			log.info("Forward with event " + e.getId() + ": " + e.getString()
					+ " starts...");

			listenersBeforeUndoEvent(e);

			e.doEvent(_arena);
			e.setExecuted(true);

			listenersAfterUndoEvent(e);

			res = _current;
			log.info("Forward with event " + e.getId() + ": " + e.getString()
					+ " done!");
		}
		return res;
	}

	/**
	 * Undoes the last event executed
	 * 
	 * @return The new current position
	 */
	public int backward() {
		int res = -1;

		if (_current > 0 && _events.size() > 0) {
			_current--;
			Event e = _events.get(_current);

			log.info("Backward with event " + e.getId() + ": " + e.getString()
					+ " starts...");

			listenersBeforeUndoEvent(e);

			e.undoEvent();
			e.setExecuted(false);

			listenersAfterUndoEvent(e);

			res = _current;
			log.info("Backward with event " + e.getId() + ": " + e.getString()
					+ " done!");
		}

		return res;
	}

	private void listenersEventSizeChanged(int size) {
		if (_listeners != null) {
			for (EventFlowListener listener : _listeners) {
				listener.eventSizeChanged(size);
			}
		}
	}

	private void listenersAfterUndoEvent(Event e) {
		if (_listeners != null) {
			for (EventFlowListener listener : _listeners) {
				listener.afterUndoEvent(e);
			}
		}
	}

	private void listenersBeforeUndoEvent(Event e) {
		if (_listeners != null) {
			for (EventFlowListener listener : _listeners) {
				listener.beforeUndoEvent(e);
			}
		}
	}

	public void addListener(EventFlowListener listener) {
		_listeners.add(listener);
	}

	public void removeListener(EventFlowListener listener) {
		_listeners.remove(listener);
	}
	
	public void removeAllListener() {
		_listeners.clear();
	}

}
