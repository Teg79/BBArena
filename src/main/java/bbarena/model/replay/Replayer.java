package bbarena.model.replay;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bbarena.model.Choice;
import bbarena.model.Coach;
import bbarena.model.RuleSet;
import bbarena.model.event.Event;
import bbarena.model.event.EventManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static bbarena.model.replay.ReplayChoice.EXIT;
import static bbarena.model.replay.ReplayChoice.NEXT;
import static bbarena.model.replay.ReplayChoice.PREV;

public class Replayer implements RuleSet {

	private static final Logger _log = LoggerFactory.getLogger(Replayer.class);
	private List<Event> _events;

	public Replayer(List<Event> events) {
		_events = events;
	}

	@Override
	public void start(EventManager eventManager,
			List<Coach> coaches) {
		boolean end = false;
		Coach coach = coaches.get(0);
		for (Event event : _events) {
			eventManager.putEvent(event);
		}
		while (!end) {
			Choice choice = coach.choice("Prev or Next?", buildChoices(eventManager));

			if (choice.equals(ReplayChoice.EXIT)) {
				end = true;

			} else if (choice.equals(ReplayChoice.PREV)) {
				prev(eventManager);

			} else {
				next(eventManager);

			}
		}
	}

	private Set<Choice> buildChoices(EventManager eventManager) {
		Set<Choice> res = new HashSet<>();
		
		if (eventManager.getCurrentPosition() == 0) {
			res.add(EXIT);
			res.add(NEXT);
		} else if (eventManager.getCurrentPosition() == eventManager.getEventsSize()) {
			res.add(EXIT);
			res.add(PREV);
		} else {
			res.add(EXIT);
			res.add(NEXT);
			res.add(PREV);
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
