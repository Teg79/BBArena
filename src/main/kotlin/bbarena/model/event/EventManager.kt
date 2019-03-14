package bbarena.model.event

import bbarena.model.Arena
import org.slf4j.LoggerFactory
import java.io.Serializable
import java.util.*

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
class EventManager(val arena: Arena) : Serializable {

    protected var log = LoggerFactory.getLogger("arena.events")
    private val _listeners = ArrayList<EventFlowListener>()
    private var _events: MutableList<Event> = ArrayList()
    var currentPosition = 0
        private set

    val eventsSize: Int
        get() = _events.size

    val lastEvent: Event?
        get() {
            var res: Event? = null
            if (currentPosition > 0) {
                res = _events[currentPosition - 1]
            }
            return res
        }

    val nextEvent: Event
        get() = _events[currentPosition]

    /**
     * Adds an event to the time line. The event will be executed when forward
     * is called and the current position is the position before this event.
     * Here the event id will be set to the previous size of the event list and
     * the id will be returned.
     *
     * @param event
     * The event to add to the line
     * @return The Id assigned to the event
     */
    fun putEvent(event: Event): Int {
        val res = _events.size
        event.id = res
        _events.add(event)

        listenersEventSizeChanged(_events.size)

        log.debug("Added Event " + event.toString())
        return res
    }

    /**
     * Removes an event from the time line. If the event is already executed
     * then it will be undone before to remove it.
     *
     */
    fun popEvent() {
        if (currentPosition == _events.size - 1) {
            backward()
        }

        _events = _events.subList(0, _events.size)

        listenersEventSizeChanged(_events.size)

        log.debug("Removed last Event")
    }

    fun forward(event: Event): Int {
        putEvent(event)
        return forward()
    }

    /**
     * Executes the next event in the list
     *
     * @return The new current position
     */
    fun forward(): Int {
        var res = -1
        if (currentPosition < _events.size) {
            val e = _events[currentPosition++]

            log.debug("Forward with event " + e.toString()
                    + " starts...")

            listenersBeforeDoEvent(e)

            e.doEvent(arena)
            e.isExecuted = true
            log.info(e.toString())
            res = currentPosition

            listenersAfterDoEvent(e)
        }
        return res
    }

    /**
     * Undoes the last event executed
     *
     * @return The new current position
     */
    fun backward(): Int {
        var res = -1

        if (currentPosition > 0 && _events.size > 0) {
            currentPosition--
            val e = _events[currentPosition]

            log.debug("Backward with event " + e.id + ": " + e.string
                    + " starts...")

            listenersBeforeUndoEvent(e)

            e.undoEvent(arena)
            e.isExecuted = false

            listenersAfterUndoEvent(e)

            res = currentPosition
            log.info("Undo " + e.toString())
        }

        return res
    }

    private fun listenersEventSizeChanged(size: Int) {
        if (_listeners != null) {
            for (listener in _listeners) {
                listener.eventSizeChanged(size)
            }
        }
    }

    private fun listenersAfterUndoEvent(e: Event) {
        if (_listeners != null) {
            for (listener in _listeners) {
                listener.afterUndoEvent(e)
            }
        }
    }

    private fun listenersBeforeUndoEvent(e: Event) {
        if (_listeners != null) {
            for (listener in _listeners) {
                listener.beforeUndoEvent(e)
            }
        }
    }

    private fun listenersAfterDoEvent(e: Event) {
        if (_listeners != null) {
            for (listener in _listeners) {
                listener.afterDoEvent(e)
            }
        }
    }

    private fun listenersBeforeDoEvent(e: Event) {
        if (_listeners != null) {
            for (listener in _listeners) {
                listener.beforeDoEvent(e)
            }
        }
    }

    fun addListener(listener: EventFlowListener): EventManager {
        _listeners.add(listener)
        listener.setEventManager(this)
        return this
    }

    fun removeListener(listener: EventFlowListener) {
        _listeners.remove(listener)
    }

    fun removeAllListener() {
        _listeners.clear()
    }

    companion object {

        private const val serialVersionUID = -2236209545925666897L
    }

}
