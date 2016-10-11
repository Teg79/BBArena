package net.sf.bbarena.model.replay;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	private Set<ReplayChoice> buildChoices(EventManager eventManager) {
		Set<ReplayChoice> res = new HashSet<>();
		
		if (eventManager.getCurrentPosition() == 0) {
			res.add(ReplayChoice.EXIT);
			res.add(ReplayChoice.NEXT);
		} else if (eventManager.getCurrentPosition() == eventManager.getEventsSize()) {
			res.add(ReplayChoice.EXIT);
			res.add(ReplayChoice.PREV);
		} else {
			res.add(ReplayChoice.EXIT);
			res.add(ReplayChoice.NEXT);
			res.add(ReplayChoice.PREV);
		}
		
		return res;
	}

	private boolean next(EventManager eventManager) {
		boolean last = false;
		int nextPos = eventManager.getCurrentPosition() + 1;
		if (_events != null && _events.size() >= nextPos) {
			eventManager.forward();
		} else {
			last = true;
			_log.info("No more events");
		}
		return last;
	}

	private boolean prev(EventManager eventManager) {
		boolean begin = false;
		if (eventManager.getCurrentPosition() == 0) {
			begin = true;
		} else {
			eventManager.backward();
		}
		return begin;
	}

}
