package net.sf.bbarena.rules.crap;

import java.util.List;

import net.sf.bbarena.model.Coach;
import net.sf.bbarena.model.RuleSet;
import net.sf.bbarena.model.event.EventManager;

public class Crap implements RuleSet<CrapChoice> {

	@Override
	public void start(EventManager eventManager, List<Coach<CrapChoice>> coaches) {
		preMatchSequence(eventManager, coaches);
		theMatch(eventManager, coaches);
		postMatchSequence(eventManager, coaches);
	}

	/*
	 * PRE MATCH SEQUENCE
	 */
	private void preMatchSequence(EventManager eventManager,
			List<Coach<CrapChoice>> coaches) {
		theWeather(eventManager, coaches);
		pettyCash(eventManager, coaches);
		inducements(eventManager, coaches);
	}

	private void inducements(EventManager eventManager,
			List<Coach<CrapChoice>> coaches) {
		// TODO Auto-generated method stub
		
	}

	private void pettyCash(EventManager eventManager,
			List<Coach<CrapChoice>> coaches) {
		// TODO Auto-generated method stub
		
	}

	private void theWeather(EventManager eventManager,
			List<Coach<CrapChoice>> coaches) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * POST MATCH SEQUENCE
	 */
	private void postMatchSequence(EventManager eventManager,
			List<Coach<CrapChoice>> coaches) {
		improvementRolls(eventManager, coaches);
		updateTeamRoster(eventManager, coaches);
	}

	private void improvementRolls(EventManager eventManager,
			List<Coach<CrapChoice>> coaches) {
		// TODO Auto-generated method stub
		
	}

	private void updateTeamRoster(EventManager eventManager,
			List<Coach<CrapChoice>> coaches) {
		updatePlayers(eventManager, coaches);
		generateWinnings(eventManager, coaches);
		spirallingExpenses(eventManager, coaches);
		fanfactor(eventManager, coaches);
		buyAndFire(eventManager, coaches);
		confirmJourneymen(eventManager, coaches);
		bringJourneymen(eventManager, coaches);
		newTeamValue(eventManager, coaches);
	}

	private void newTeamValue(EventManager eventManager,
			List<Coach<CrapChoice>> coaches) {
		// TODO Auto-generated method stub
		
	}

	private void bringJourneymen(EventManager eventManager,
			List<Coach<CrapChoice>> coaches) {
		// TODO Auto-generated method stub
		
	}

	private void confirmJourneymen(EventManager eventManager,
			List<Coach<CrapChoice>> coaches) {
		// TODO Auto-generated method stub
		
	}

	private void buyAndFire(EventManager eventManager,
			List<Coach<CrapChoice>> coaches) {
		// TODO Auto-generated method stub
		
	}

	private void fanfactor(EventManager eventManager,
			List<Coach<CrapChoice>> coaches) {
		// TODO Auto-generated method stub
		
	}

	private void spirallingExpenses(EventManager eventManager,
			List<Coach<CrapChoice>> coaches) {
		// TODO Auto-generated method stub
		
	}

	private void generateWinnings(EventManager eventManager,
			List<Coach<CrapChoice>> coaches) {
		// TODO Auto-generated method stub
		
	}

	private void updatePlayers(EventManager eventManager,
			List<Coach<CrapChoice>> coaches) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * THE MATCH
	 */
	private void theMatch(EventManager eventManager,
			List<Coach<CrapChoice>> coaches) {
		// TODO Auto-generated method stub
		
	}

}
