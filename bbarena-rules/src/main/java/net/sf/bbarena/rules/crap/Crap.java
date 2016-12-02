package net.sf.bbarena.rules.crap;

import net.sf.bbarena.model.*;
import net.sf.bbarena.model.choice.Concede;
import net.sf.bbarena.model.choice.Continue;
import net.sf.bbarena.model.choice.EndTurn;
import net.sf.bbarena.model.choice.FlipRoll;
import net.sf.bbarena.model.event.Event;
import net.sf.bbarena.model.event.EventManager;
import net.sf.bbarena.model.event.game.*;
import net.sf.bbarena.model.pitch.*;
import net.sf.bbarena.model.team.Player;
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
import java.util.stream.Collectors;

import static net.sf.bbarena.model.Match.Status.*;
import static net.sf.bbarena.model.event.Die.*;

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

        coaches.stream().forEach(crapChoiceCoach -> crapChoiceCoach.setChoiceFilter(new CrapChoiceFilter(eventManager.getArena())));

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
        eventManager.forward(matchStatusChangeEvent);

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
        eventManager.forward(event);
    }

    /*
     * POST MATCH SEQUENCE
     */
    private void postMatchSequence(EventManager eventManager,
                                   List<Coach> coaches) {
        improvementRolls(eventManager, coaches);
        updateTeamRoster(eventManager, coaches);

        MatchStatusChangeEvent matchStatusChangeEvent = new MatchStatusChangeEvent(FINISHED);
        eventManager.forward(matchStatusChangeEvent);
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
        eventManager.forward(matchStatusChangeEvent);

        fame(eventManager, coaches);
        teamEntersArena(eventManager, coaches, 0);
        teamEntersArena(eventManager, coaches, 1);

        NewHalfEvent newHalfEvent = new NewHalfEvent();
        RollResult flip = Roll.roll(1, D2, newHalfEvent, "Flip Coin", "Match");
        eventManager.forward(newHalfEvent);

        int choiceCoach = flip.getSum() - 1;
        Choice choice = coaches.get(choiceCoach).choice("Kick or Receive?", FlipRoll.values());

        int firstCoach = 0;
        int setUpCoach = 1;
        if ((choiceCoach == 0 && choice == FlipRoll.KICK) || (choiceCoach == 1 && choice == FlipRoll.RECEIVE)) {
            // Coach 0 receive
            firstCoach = 1;
            setUpCoach = 0;
        }

        setUpTeam(eventManager, coaches, setUpCoach);
        setUpTeam(eventManager, coaches, firstCoach);

        int nextCoach = firstCoach;
        int waitingCoach = setUpCoach;
        int drive = 1;
        while (isGamePlaying(eventManager)) {

            kickOff(eventManager, coaches, firstCoach, setUpCoach);
            while (eventManager.getArena().getMatch().getCurrentDrive() == drive) {
                playTurn(eventManager, nextCoach, waitingCoach, coaches);
                waitingCoach = nextCoach;
                nextCoach = switchCoach(nextCoach);
            }

            drive++;
        }
    }

    private void playTurn(EventManager eventManager, int playingCoachPos, int waitingCoachPos, List<Coach> coaches) {
        moveTurnMarker(eventManager, playingCoachPos);

        Coach playingCoach = coaches.get(playingCoachPos);
        Choice startTurn = playingCoach.choice("Continue?", new Continue()/*, new Concede()*/);

        if (startTurn instanceof Concede) {
            // end match
        } else {

            Set<Choice> choices = new LinkedHashSet<>();
            playingCoach.getTeam().getPlayers().stream().filter(player ->
                    player.getTeam().equals(playingCoach.getTeam())
                            && player.isOnThePitch()
                            && player.getPitchStatus() != Player.PlayerPitchStatus.PRONE
            ).forEach(player -> choices.add(player));
            choices.add(new EndTurn());

            Choice player;
            Event lastEvent;
            boolean endTurn = false;
            do {
                player = playingCoach.choice("Next player?", choices);
                choices.remove(player);

                if (player instanceof Player) {
                    playerTurn(player, eventManager, playingCoach);
                    if (choices.size() == 0 || (choices.size() == 1 && choices.iterator().next() instanceof EndTurn)) {
                        eventManager.forward(new EndTurnEvent());
                    }
                } else {
                    eventManager.forward(new EndTurnEvent());
                }
                lastEvent = eventManager.getLastEvent();

                if (lastEvent instanceof EndTurnEvent) {
                    int half = eventManager.getArena().getHalf();
                    List<TurnMarker> turnMarkers = eventManager.getArena().getTurnMarkers();
                    if (turnMarkers.get(0).getTurn() == 8 && turnMarkers.get(1).getTurn() == 8) {
                        if (half == 2) {
                            EndDriveEvent endDriveEvent = new EndDriveEvent();
                            eventManager.forward(endDriveEvent);
                            EndGameEvent endGameEvent = new EndGameEvent();
                            eventManager.forward(endGameEvent);
                        } else {
                            EndDriveEvent endDriveEvent = new EndDriveEvent();
                            eventManager.forward(endDriveEvent);
                            NewHalfEvent newHalfEvent = new NewHalfEvent();
                            eventManager.forward(newHalfEvent);
                        }
                    }
                    endTurn = true;
                } else if (lastEvent instanceof TurnoverEvent) {
                    eventManager.forward(new EndTurnEvent());
                    endTurn = true;
                }
            }
            while (!endTurn);
        }
    }

    private void playerTurn(Choice player, EventManager eventManager, Coach playingCoach) {
        // TODO: player action
    }

    private void moveTurnMarker(EventManager eventManager, int playingCoachPos) {
        NewTurnEvent newTurnEvent = new NewTurnEvent();
        newTurnEvent.setCoachPos(playingCoachPos);
        eventManager.forward(newTurnEvent);
    }

    private boolean isGamePlaying(EventManager eventManager) {
        return eventManager.getArena().getMatch().getStatus() == PLAYING;
    }

    private int switchCoach(int coach) {
        return coach == 0 ? 1 : 0;
    }

    private void setUpTeam(EventManager eventManager, List<Coach> coaches, int team) {

        Pitch pitch = eventManager.getArena().getPitch();
        long playablePlayers = pitch.getDugouts().get(team).getPlayers(DefaultDogout.BloodBowlDugoutRoom.RESERVES).stream().filter(player -> player.isPlayable()).count();

        TeamSetUp setUpChoice = null;
        boolean valid;
        do {
            Choice choice = coaches.get(team).choice("Set Up Players", new TeamSetUp());

            if (choice != null) {
                setUpChoice = (TeamSetUp) choice;
                valid = validateSetUp(setUpChoice, playablePlayers, pitch);
                if (!valid) {
                    log.warn("Invalid Set Up");
                }
            } else {
                valid = true;
            }
        } while (!valid);

        final TeamSetUp setUp = setUpChoice;
        setUpChoice.getSetUp().keySet().stream().forEach(key -> {
            Coordinate coordinate = setUp.getSetUp().get(key);
            PutPlayerInPitchEvent putPlayerInPitchEvent = new PutPlayerInPitchEvent(key, coordinate.getX(), coordinate.getY());
            eventManager.forward(putPlayerInPitchEvent);
        });
    }

    private boolean validateSetUp(TeamSetUp teamSetUp, long playablePlayers, Pitch pitch) {
        final boolean[] res = {true};
        final int[] leftWide = {0};
        final int[] rightWide = {0};
        final int[] los = {0};

        check:
        {
            if (teamSetUp.getSetUp().size() != Math.min(playablePlayers, 11)) {
                res[0] = false;
                break check;
            }

            teamSetUp.getSetUp().keySet().stream().forEach(key -> {
                Coordinate coordinate = teamSetUp.getSetUp().get(key);
                Square square = pitch.getSquare(coordinate);
                if (square.getTeamOwner().getPlayers().stream().filter(player -> player.getId() == key).count() == 0) {
                    res[0] = false;
                }
                Square.SquareType squareType = square.getType();
                switch (squareType) {
                    case LOS:
                        los[0]++;
                        break;
                    case RIGHT_WIDE_ZONE:
                        rightWide[0]++;
                        break;
                    case LEFT_WIDE_ZONE:
                        leftWide[0]++;
                        break;
                }
            });

            if (los[0] < Math.min(3, playablePlayers)) {
                res[0] = false;
                break check;
            }

            if (rightWide[0] > 2) {
                res[0] = false;
                break check;
            }
        }

        return res[0];
    }

    private void teamEntersArena(EventManager eventManager, List<Coach> coaches, int team) {
        Team fullTeam = coaches.get(team).getTeam();

//        refillRerolls(eventManager, team, fullTeam);

        fullTeam.getPlayers().stream().filter(player -> !player.isMng()).forEach(player -> {
            PutPlayerInDugoutEvent putPlayer = new PutPlayerInDugoutEvent(player.getId(), DefaultDogout.BloodBowlDugoutRoom.RESERVES.toString());
            eventManager.forward(putPlayer);
        });
    }

//    private void refillRerolls(EventManager eventManager, int team, Team fullTeam) {
//        ChangeRerollEvent rerollEvent = new ChangeRerollEvent(team, fullTeam.getReRolls());
//        eventManager.forward(rerollEvent);
//    }

    private void fame(EventManager eventManager, List<Coach> coaches) {

        FansEvent fansTeam1 = new FansEvent(0);
        Team team1 = coaches.get(0).getTeam();
        int fans1 = (team1.getFanFactor() + Roll.roll(2, D6, fansTeam1, "Fans", "Coach " + team1.getCoach().getName()).getSum()) * 10000;
        fansTeam1.setFans(fans1);
        eventManager.forward(fansTeam1);

        FansEvent fansTeam2 = new FansEvent(1);
        Team team2 = coaches.get(1).getTeam();
        int fans2 = (team2.getFanFactor() + Roll.roll(2, D6, fansTeam2, "Fans", "Coach " + team2.getCoach().getName()).getSum()) * 10000;
        fansTeam2.setFans(fans2);
        eventManager.forward(fansTeam2);

        int fame = 0;
        if (fans1 > fans2) {
            fame = 1;
        }
        if (fans1 >= (fans2 * 2)) {
            fame = 2;
        }
        if (fans2 > fans1) {
            fame = -1;
        }
        if (fans2 >= (fans1 * 2)) {
            fame = -2;
        }
        FameEvent fameEvent = new FameEvent();
        fameEvent.setFameCoach0(fame);
        fameEvent.setFameCoach1(fame * -1);

        eventManager.forward(fameEvent);
    }

    private void kickOff(EventManager eventManager, List<Coach> coaches, int firstCoach, int kickingCoach) {

        // Place the ball
        Pitch pitch = eventManager.getArena().getPitch();
        Set<Square> landingSquares = pitch.getTeamSquares(coaches.get(firstCoach).getTeam());
        Square destination = (Square) coaches.get(kickingCoach).choice("Place Ball", landingSquares.toArray(new Square[landingSquares.size()]));

        int ballId = pitch.getBall().getId();
        Coach kickerCoach = coaches.get(kickingCoach);

        KickOffBallEvent kickOffBallEvent = new KickOffBallEvent(ballId, destination.getCoords().getX(), destination.getCoords().getY());
        eventManager.forward(kickOffBallEvent);

        // Scatter the ball
        ScatterBallEvent scatterBallEvent = new ScatterBallEvent(ballId);

        Integer directionRoll = Roll.roll(1, DS, scatterBallEvent, "Scatter direction", kickerCoach.getName()).getSum();
        Direction direction = Direction.getDirection(directionRoll);

        Integer distance = Roll.roll(1, D6, scatterBallEvent, "Scatter distance", kickerCoach.getName()).getSum();

        scatterBallEvent.setDirection(direction);
        scatterBallEvent.setDistance(distance);
        scatterBallEvent.setType(BallMove.BallMoveType.KICK_OFF);
        eventManager.forward(scatterBallEvent);

        KickOffEvent kickOffEvent = new KickOffEvent(); // Base event, extends for each kick off event
        Integer kickOffRoll = Roll.roll(2, D6, scatterBallEvent, "Kick Off", kickerCoach.getName()).getSum();

        // Resolve kickoff event
        // TODO: implement the kickoff table
        switch (kickOffRoll) {
            case 2:
                // Get the Ref
                break;
            case 3:
                // Riot
                break;
            case 4:
                // Perfect Defence
                break;
            case 5:
                // High Kick
                break;
            case 6:
                // Cheering Fans
                break;
            case 7:
                // Changing Weather
                break;
            case 8:
                // Brilliant Coaching
                break;
            case 9:
                // Quick Snap!
                break;
            case 10:
                // Blitz!
                break;
            case 11:
                // Throw a Rock
                break;
            case 12:
                // Pitch Invasion
                break;
        }

        boolean endBouncing = false;
        boolean bounced = false;
        Square lastSquare;
        SquareDestination squareDestination = scatterBallEvent.getDestination();
        Coordinate lastValidSquare = squareDestination.getLastValidSquare();
        lastSquare = eventManager.getArena().getPitch().getSquare(lastValidSquare);
        do {
            if ((squareDestination.isOutOfPitch() || lastSquare == null || lastSquare.getTeamOwner().equals(kickerCoach.getTeam()))) {
                // touchback
                CatchBallEvent catchBallEvent = new CatchBallEvent(ballId);
                List<Player> playerStream = pitch.getPlayers().stream().filter(player -> player.getTeam().getId() == coaches.get(firstCoach).getTeam().getId()).collect(Collectors.toList());
                Player touchbackPlayer = (Player) coaches.get(firstCoach).choice("Touchback", playerStream.toArray(new Player[playerStream.size()]));
                catchBallEvent.setPlayer(touchbackPlayer);
                eventManager.forward(catchBallEvent);
                endBouncing = true;
            } else {
                if (bounced) {
                    endBouncing = true;
                }
                if (lastSquare.hasPlayer()) {
                    endBouncing = AgilityTable.catchRoll(eventManager, coaches.get(firstCoach));
                }
                if (!endBouncing && !bounced) {
                    scatterBallEvent = Scatter.buildScatterBallEvent(ballId, lastSquare, kickerCoach);
                    eventManager.forward(scatterBallEvent);
                    squareDestination = scatterBallEvent.getDestination();
                    lastValidSquare = squareDestination.getLastValidSquare();
                    lastSquare = eventManager.getArena().getPitch().getSquare(lastValidSquare);
                    if (!lastSquare.hasPlayer()) {
                        bounced = true;
                    }
                }
            }
        } while (!endBouncing);

    }

}
