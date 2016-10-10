package net.sf.bbarena.model;

import java.util.List;

import net.sf.bbarena.model.event.EventManager;

public interface RuleSet<C extends Choice> {

	public void start(EventManager eventManager, List<Coach<C>> coaches);
	
}
