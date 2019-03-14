package bbarena.model.event

import bbarena.model.Arena
import bbarena.model.event.game.*
import bbarena.model.exception.EventException
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class EventFactory {

    enum class EventType(val eventClass: Class<out Event>) {

        MOVE_PLAYER(MovePlayerEvent::class.java),
        PASS_BALL(PassBallEvent::class.java),
        PUT_PLAYER_IN_DUGOUT(PutPlayerInDugoutEvent::class.java),
        PUT_PLAYER_IN_PITCH(PutPlayerInPitchEvent::class.java),
        PICK_UP_BALL_OWNER(PickUpBallEvent::class.java);

    }

    // TODO: The string must be deserialized and the right constructor from the right event must be called
    fun getEvent(type: EventType, arena: Arena, serializedEvent: String): Event {
        val eventClass = type.eventClass
        return try {
            val c = eventClass.getConstructor(Arena::class.java, String::class.java)
            c.newInstance(arena, serializedEvent)
        } catch (e: Exception) {
            val msg = ("Unable to load event " + type.toString() + ": "
                    + e.message)
            logger.error(msg, e)
            throw EventException(msg)
        }
    }

}
