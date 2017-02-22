package bbarena.model;

import java.util.List;

import bbarena.model.event.EventManager;

public interface RuleSet {

	void start(EventManager eventManager, List<Coach> coaches);
	
}
