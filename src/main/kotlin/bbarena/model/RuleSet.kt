package bbarena.model

import bbarena.model.event.EventManager

interface RuleSet {

    fun start(eventManager: EventManager,
              coaches: List<Coach>)

}
