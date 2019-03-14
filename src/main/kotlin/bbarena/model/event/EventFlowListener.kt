package bbarena.model.event

/**
 * Implementations of this interface will be notified of EventLine changes
 *
 * @author f.bellentani
 */
interface EventFlowListener {

    fun beforeDoEvent(e: Event)

    fun afterDoEvent(e: Event)

    fun beforeUndoEvent(e: Event)

    fun afterUndoEvent(e: Event)

    fun eventSizeChanged(newSize: Int)

    fun setEventManager(eventManager: EventManager)

}
