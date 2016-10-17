package net.sf.bbarena.rules.crap;

import net.sf.bbarena.model.*;
import net.sf.bbarena.model.event.EventManager;
import net.sf.bbarena.model.event.game.ChangeWeatherEvent;
import net.sf.bbarena.model.event.game.FameEvent;
import net.sf.bbarena.model.event.game.FansEvent;
import net.sf.bbarena.model.event.game.MatchStatusChangeEvent;
import net.sf.bbarena.model.team.Team;
import net.sf.bbarena.rules.crap.listeners.Blizzard;
import net.sf.bbarena.rules.crap.listeners.PouringRain;
import net.sf.bbarena.rules.crap.listeners.SwelteringHeat;
import net.sf.bbarena.rules.crap.listeners.VerySunny;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static net.sf.bbarena.model.Match.Status.*;
import static net.sf.bbarena.model.event.Die.D6;

public class Crap implements RuleSet {

    private static final Logger log = LoggerFactory.getLogger(Crap.class);

    @Override
    public void start(EventManager eventManager, List<Coach> coaches) {
        log.info("Loading CRAP rules...");
        eventManager
                .addListener(new Blizzard())
                .addListener(new PouringRain())
                .addListener(new VerySunny())
                .addListener(new SwelteringHeat());

        coaches.stream().forEach(crapChoiceCoach -> crapChoiceCoach.setChoiceFilter(new CrapChoiceChoiceFilter(eventManager.getArena())));

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
                                  List<Coach> coaches) {
        MatchStatusChangeEvent matchStatusChangeEvent = new MatchStatusChangeEvent(STARTING);
        eventManager.putEvent(matchStatusChangeEvent);

        theWeather(eventManager, coaches);
        pettyCash(eventManager, coaches);
        inducements(eventManager, coaches);
    }

    private void inducements(EventManager eventManager,
                             List<Coach> coaches) {
        // TODO Auto-generated method stub

    }

    private void pettyCash(EventManager eventManager,
                           List<Coach> coaches) {
        // TODO Auto-generated method stub

    }

    private void theWeather(EventManager eventManager,
                            List<Coach> coaches) {
        ChangeWeatherEvent event = new ChangeWeatherEvent();
        Integer result = Roll.roll(2, D6, event, "Weather", "Match").getSum();
        Weather weather = WeatherTable.getWeather(result);
        event.setNewWeather(weather);
        eventManager.putEvent(event);
    }

    /*
     * POST MATCH SEQUENCE
     */
    private void postMatchSequence(EventManager eventManager,
                                   List<Coach> coaches) {
        MatchStatusChangeEvent matchStatusChangeEvent = new MatchStatusChangeEvent(ENDING);
        eventManager.putEvent(matchStatusChangeEvent);

        improvementRolls(eventManager, coaches);
        updateTeamRoster(eventManager, coaches);

        matchStatusChangeEvent = new MatchStatusChangeEvent(FINISHED);
        eventManager.putEvent(matchStatusChangeEvent);
    }

    private void improvementRolls(EventManager eventManager,
                                  List<Coach> coaches) {
        // TODO Auto-generated method stub

    }

    private void updateTeamRoster(EventManager eventManager,
                                  List<Coach> coaches) {
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
                              List<Coach> coaches) {
        // TODO Auto-generated method stub

    }

    private void bringJourneymen(EventManager eventManager,
                                 List<Coach> coaches) {
        // TODO Auto-generated method stub

    }

    private void confirmJourneymen(EventManager eventManager,
                                   List<Coach> coaches) {
        // TODO Auto-generated method stub

    }

    private void buyAndFire(EventManager eventManager,
                            List<Coach> coaches) {
        // TODO Auto-generated method stub

    }

    private void fanfactor(EventManager eventManager,
                           List<Coach> coaches) {
        // TODO Auto-generated method stub

    }

    private void spirallingExpenses(EventManager eventManager,
                                    List<Coach> coaches) {
        // TODO Auto-generated method stub

    }

    private void generateWinnings(EventManager eventManager,
                                  List<Coach> coaches) {
        // TODO Auto-generated method stub

    }

    private void updatePlayers(EventManager eventManager,
                               List<Coach> coaches) {
        // TODO Auto-generated method stub

    }

    /*
     * THE MATCH
     */
    private void theMatch(EventManager eventManager,
                          List<Coach> coaches) {
        MatchStatusChangeEvent matchStatusChangeEvent = new MatchStatusChangeEvent(PLAYING);
        eventManager.putEvent(matchStatusChangeEvent);

        fame(eventManager, coaches);
        // TODO Auto-generated method stub

    }

    private void fame(EventManager eventManager, List<Coach> coaches) {

        FansEvent fansTeam1 = new FansEvent(0);
        Team team1 = coaches.get(0).getTeam();
        int fans1 = (team1.getFanFactor() + Roll.roll(2, D6, fansTeam1, "Fans", "Coach " + team1.getCoach().getName()).getSum()) * 10000;
        fansTeam1.setFans(fans1);
        eventManager.putEvent(fansTeam1);

        FansEvent fansTeam2 = new FansEvent(1);
        Team team2 = coaches.get(1).getTeam();
        int fans2 = (team2.getFanFactor() + Roll.roll(2, D6, fansTeam2, "Fans", "Coach " + team2.getCoach().getName()).getSum()) * 10000;
        fansTeam2.setFans(fans2);
        eventManager.putEvent(fansTeam2);

        int fame = 0;
        if (fans1 > fans2) {
            fame = 1;
        }
        if (fans1 >= (fans2 * 2)) {
            fame = 2;
        }
        FameEvent fameEvent = new FameEvent();
        fameEvent.setFameCoach0(fame);
        fameEvent.setFameCoach1(fame * -1);

        eventManager.putEvent(fameEvent);
    }

    private class CrapChoiceChoiceFilter implements ChoiceFilter {

        private final Arena _arena;

        CrapChoiceChoiceFilter(Arena arena) {
            _arena = arena;
        }

        @Override
        public Set<Choice> filter(Set<Choice> choices) {
            Set<Choice> res;
            if (Weather.getWeather(Weather.WeatherType.BLIZZARD).equals(_arena.getWeather())) {
                res = new LinkedHashSet<>();
                choices.stream().forEach(crapChoice -> {
                    if (!crapChoice.equals(RangeRuler.Range.BOMB) && !crapChoice.equals(RangeRuler.Range.LONG)) {
                        res.add(crapChoice);
                    }
                });
            } else {
                res = choices;
            }
            return res;
        }
    }
}
