package bbarena.model.replay

import bbarena.model.Choice
import bbarena.model.Coach
import bbarena.model.RuleSet
import bbarena.model.event.Event
import bbarena.model.event.EventManager
import bbarena.model.replay.ReplayChoice.*
import org.slf4j.LoggerFactory
import java.util.*

class Replayer(private val _events: List<Event>?) : RuleSet {

    override fun start(eventManager: EventManager,
                       coaches: List<Coach>) {
        var end = false
        val coach = coaches[0]
        for (event in _events!!) {
            eventManager.putEvent(event)
        }
        while (!end) {
            val choice = coach.choice("Prev or Next?", buildChoices(eventManager))

            if (choice == ReplayChoice.EXIT) {
                end = true

            } else if (choice == ReplayChoice.PREV) {
                prev(eventManager)

            } else {
                next(eventManager)

            }
        }
    }

    private fun buildChoices(eventManager: EventManager): Set<Choice> {
        val res = HashSet<Choice>()

        if (eventManager.currentPosition == 0) {
            res.add(EXIT)
            res.add(NEXT)
        } else if (eventManager.currentPosition == eventManager.eventsSize) {
            res.add(EXIT)
            res.add(PREV)
        } else {
            res.add(EXIT)
            res.add(NEXT)
            res.add(PREV)
        }

        return res
    }

    private fun next(eventManager: EventManager): Boolean {
        var last = false
        val nextPos = eventManager.currentPosition + 1
        if (_events != null && _events.size >= nextPos) {
            eventManager.forward()
        } else {
            last = true
            _log.info("No more events")
        }
        return last
    }

    private fun prev(eventManager: EventManager): Boolean {
        var begin = false
        if (eventManager.currentPosition == 0) {
            begin = true
        } else {
            eventManager.backward()
        }
        return begin
    }

    companion object {

        private val _log = LoggerFactory.getLogger(Replayer::class.java)
    }

}
