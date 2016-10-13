package net.sf.bbarena.model.event;

import java.lang.reflect.Constructor;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.event.game.MovePlayerEvent;
import net.sf.bbarena.model.event.game.PassBallEvent;
import net.sf.bbarena.model.event.game.PickUpBallEvent;
import net.sf.bbarena.model.event.game.PutPlayerInDugoutEvent;
import net.sf.bbarena.model.event.game.PutPlayerInPitchEvent;
import net.sf.bbarena.model.exception.EventException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventFactory {

	private static final Logger log = LoggerFactory.getLogger(EventFactory.class);

	public enum EventType {

		MOVE_PLAYER(MovePlayerEvent.class),
		PASS_BALL(PassBallEvent.class),
		PUT_PLAYER_IN_DUGOUT(PutPlayerInDugoutEvent.class),
		PUT_PLAYER_IN_PITCH(PutPlayerInPitchEvent.class),
		PICK_UP_BALL_OWNER(PickUpBallEvent.class);

		private Class<? extends Event> eventClass = null;

		private EventType(Class<? extends Event> eventClass) {
			this.eventClass = eventClass;
		}

		private Class<? extends Event> getEventClass() {
			return eventClass;
		}

	}

	// TODO: The string must be deserialized and the right constructor from the right event must be called
	public Event getEvent(EventType type, Arena arena, String serializedEvent)
			throws EventException {
		Event res = null;
		Class<? extends Event> eventClass = type.getEventClass();
		try {
			Constructor<? extends Event> c = eventClass.getConstructor(new Class[] {
					Arena.class, String.class });
			res = c.newInstance(new Object[] { arena, serializedEvent });
		} catch (Exception e) {
			String msg = "Unable to load event " + type.toString() + ": "
					+ e.getMessage();
			log.error(msg, e);
			throw new EventException(msg, e);
		}

		return res;
	}

}
