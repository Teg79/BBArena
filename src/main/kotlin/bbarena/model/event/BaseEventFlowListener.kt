package bbarena.model.event

/**
 * Implementations of this interface will be notified of EventLine changes
 *
 * @author f.bellentani
 */
abstract class BaseEventFlowListener : EventFlowListener {

    private var _eventManager: EventManager? = null

    override fun setEventManager(eventManager: EventManager) {
        _eventManager = eventManager
    }

    fun getEventManager(): EventManager? {
        return _eventManager
    }
}
