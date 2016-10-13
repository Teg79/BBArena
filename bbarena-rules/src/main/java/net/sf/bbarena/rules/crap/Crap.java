package net.sf.bbarena.rules.crap;

import java.util.List;

import net.sf.bbarena.model.*;
import net.sf.bbarena.model.event.Die;
import net.sf.bbarena.model.event.EventManager;
import net.sf.bbarena.model.event.game.ChangeWeatherEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Crap implements RuleSet<CrapChoice> {

	private static final Logger log = LoggerFactory.getLogger(Crap.class);

	@Override
	public void start(EventManager eventManager, List<Coach<CrapChoice>> coaches) {
		log.info("Starting match!");

		log.info("Pre-Match Sequence");
		preMatchSequence(eventManager, coaches);

		log.info("Play!");
		theMatch(eventManager, coaches);

		log.info("Post-Match Sequence");
		postMatchSequence(eventManager, coaches);

		log.info("Match ended!");
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
		ChangeWeatherEvent event = new ChangeWeatherEvent();
		Integer result = Roll.roll(2, Die.D6, event, "Weather", "Match").getSum();
		Weather weather = WeatherTable.getWeather(result);
        event.setNewWeather(weather);
		eventManager.putEvent(event);
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
