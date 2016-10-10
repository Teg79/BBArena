package net.sf.bbarena.model.replay;

import java.util.List;

import net.sf.bbarena.model.Coach;
import net.sf.bbarena.model.RuleSet;
import net.sf.bbarena.model.event.Event;
import net.sf.bbarena.model.event.EventManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Replayer implements RuleSet<ReplayChoice> {

	private static final Logger _log = LoggerFactory.getLogger(Replayer.class);
	private List<Event> _events;

	public Replayer(List<Event> events) {
		_events = events;
	}

	@Override
	public void start(EventManager eventManager,
			List<Coach<ReplayChoice>> coaches) {
		boolean end = false;
		Coach<ReplayChoice> coach = coaches.get(0);
		for (Event event : _events) {
			eventManager.putEvent(event);
		}
		while (!end) {
			ReplayChoice choice = coach.choice("Prev or Next?", buildChoices(eventManager));
			switch (choice) {
			case EXIT:
				end = true;
				break;
			case PREV:
				prev(eventManager);
				break;
			case NEXT:
			default:
				next(eventManager);
				break;
			}
		}
	}

	private ReplayChoice[] buildChoices(EventManager eventManager) {
		ReplayChoice[] res = null;
		
		if (eventManager.getCurrentPosition() == 0) {
			res = new ReplayChoice[]{ReplayChoice.EXIT, ReplayChoice.NEXT};
		} else if (eventManager.getCurrentPosition() == eventManager.getEventsSize()) {
			res = new ReplayChoice[]{ReplayChoice.EXIT, ReplayChoice.PREV};
		} else {
			res = new ReplayChoice[]{ReplayChoice.EXIT, ReplayChoice.PREV, ReplayChoice.NEXT};
		}
		
		return res;
	}

	private void next(EventManager eventManager) {
		int nextPos = eventManager.getCurrentPosition() + 1;
		if (_events != null && _events.size() >= nextPos) {
			eventManager.forward();
		} else {
			_log.info("No more events");
		}
	}

	private void prev(EventManager eventManager) {
		eventManager.backward();
	}

}
