package net.sf.bbarena.rules.crap;

import net.sf.bbarena.model.*;
import net.sf.bbarena.model.choice.Concede;
import net.sf.bbarena.model.choice.FlipRoll;
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
        MatchStatusChangeEvent matchStatusChangeEvent = new MatchStatusChangeEvent(ENDING);
        eventManager.forward(matchStatusChangeEvent);

        improvementRolls(eventManager, coaches);
        updateTeamRoster(eventManager, coaches);

        matchStatusChangeEvent = new MatchStatusChangeEvent(FINISHED);
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
        boolean proceed = true;

        MatchStatusChangeEvent matchStatusChangeEvent = new MatchStatusChangeEvent(PLAYING);
        eventManager.forward(matchStatusChangeEvent);

        fame(eventManager, coaches);
        teamEntersArena(eventManager, coaches, 0);
        teamEntersArena(eventManager, coaches, 1);

        NewTimeEvent newTimeEvent = new NewTimeEvent();
        RollResult flip = Roll.roll(1, D2, newTimeEvent, "Flip Coin", "Match");
        eventManager.forward(newTimeEvent);

        int choiceCoach = flip.getSum() - 1;
        Choice choice = checkChoice(coaches.get(choiceCoach).choice("Kick or Receive?", FlipRoll.values()));

        if (choice != null) {
            int firstCoach = 0;
            int setUpCoach = 1;
            if ((choiceCoach == 0 && choice == FlipRoll.KICK) || (choiceCoach == 1 && choice == FlipRoll.RECEIVE)) {
                // Coach 0 receive
                firstCoach = 1;
                setUpCoach = 0;
            }

            proceed = setUpTeam(eventManager, coaches, setUpCoach);
            if (proceed) {
                proceed = setUpTeam(eventManager, coaches, firstCoach);

                if (proceed) {
                    proceed = kickOff(eventManager, coaches, firstCoach, setUpCoach);
                }

            }
        }

    }

    private boolean kickOff(EventManager eventManager, List<Coach> coaches, int firstCoach, int kickingCoach) {
        boolean proceed = true;

        // Place the ball
        Set<Square> landingSquares = eventManager.getArena().getPitch().getTeamSquares(coaches.get(firstCoach).getTeam());
        Square destination = (Square) checkChoice(coaches.get(kickingCoach).choice("Place Ball", landingSquares.toArray(new Square[landingSquares.size()])));

        if (destination != null) {

            KickOffBallEvent kickOffBallEvent = new KickOffBallEvent(eventManager.getArena().getPitch().getBall().getId(), destination.getCoords().getX(), destination.getCoords().getY());
            String kickerCoach = coaches.get(kickingCoach).getTeam().getCoach().getName();
            Integer kickOffRoll = Roll.roll(2, D6, kickOffBallEvent, "Kick Off", kickerCoach).getSum();
            eventManager.forward(kickOffBallEvent);

            // Resolve kickoff event
            KickOffEvent kickOffEvent = new KickOffEvent(); // Base event, extends for each kick off event
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

            // Scatter the ball
            ScatterBallEvent scatterBallEvent = new ScatterBallEvent(eventManager.getArena().getPitch().getBall().getId());

            Integer directionRoll = Roll.roll(1, DS, scatterBallEvent, "Scatter direction", kickerCoach).getSum();
            Direction direction = Direction.getDirection(directionRoll);

            Integer distance = Roll.roll(1, D6, scatterBallEvent, "Scatter distance", kickerCoach).getSum();

            scatterBallEvent.setDirection(direction);
            scatterBallEvent.setDistance(distance);
            scatterBallEvent.setType(BallMove.BallMoveType.KICK_OFF);

            eventManager.forward(scatterBallEvent);

            SquareDestination squareDestination = scatterBallEvent.getDestination();
            if (squareDestination.isOutOfPitch()) {
                // touchback
                CatchBallEvent catchBallEvent = new CatchBallEvent(eventManager.getArena().getPitch().getBall().getId());
                List<Player> playerStream = eventManager.getArena().getPitch().getPlayers().stream().filter(player -> player.getTeam().getId() == coaches.get(firstCoach).getTeam().getId()).collect(Collectors.toList());
                Player touchback = (Player) coaches.get(firstCoach).choice("Touchback", playerStream.toArray(new Player[playerStream.size()]));
                catchBallEvent.setPlayer(touchback);
                eventManager.forward(catchBallEvent);
            } else {
                ScatterBallEvent scatter = scatterBall(scatterBallEvent, eventManager.getArena().getPitch().getSquare(squareDestination.getLastValidSquare()), kickerCoach);
                squareDestination = roulette(eventManager, scatter, kickerCoach);
            }
        } else {
            proceed = false;
        }

        return proceed;
    }

    private boolean setUpTeam(EventManager eventManager, List<Coach> coaches, int team) {

        Pitch pitch = eventManager.getArena().getPitch();
        long playablePlayers = pitch.getDugouts().get(team).getPlayers(DefaultDogout.BloodBowlDugoutRoom.RESERVES).stream().filter(player -> player.isPlayable()).count();

        TeamSetUp setUpChoice = null;
        boolean valid;
        do {
            Choice choice = checkChoice(coaches.get(team).choice("Set Up Players", new TeamSetUp()));

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

        if (setUpChoice != null) {
            final TeamSetUp setUp = setUpChoice;
            setUpChoice.getSetUp().keySet().stream().forEach(key -> {
                Coordinate coordinate = setUp.getSetUp().get(key);
                PutPlayerInPitchEvent putPlayerInPitchEvent = new PutPlayerInPitchEvent(key, coordinate.getX(), coordinate.getY());
                eventManager.forward(putPlayerInPitchEvent);
            });
        }
        return setUpChoice != null;
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

        refillRerolls(eventManager, team, fullTeam);

        fullTeam.getPlayers().stream().filter(player -> !player.isMng()).forEach(player -> {
            PutPlayerInDugoutEvent putPlayer = new PutPlayerInDugoutEvent(player.getId(), DefaultDogout.BloodBowlDugoutRoom.RESERVES.toString());
            eventManager.forward(putPlayer);
        });
    }

    private void refillRerolls(EventManager eventManager, int team, Team fullTeam) {
        ChangeRerollEvent rerollEvent = new ChangeRerollEvent(team, fullTeam.getReRolls());
        eventManager.forward(rerollEvent);
    }

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

    private SquareDestination roulette(EventManager eventManager, ScatterBallEvent scatterBallEvent, String coachName) {
        eventManager.forward(scatterBallEvent);
        SquareDestination destination = scatterBallEvent.getDestination();
        Coordinate lastValidSquare = destination.getLastValidSquare();

        if (destination.isOutOfPitch()) {
            ScatterBallEvent throwInBallEvent = new ScatterBallEvent(scatterBallEvent.getBallId());
            Integer throwInTemplate = Roll.roll(1, D6, throwInBallEvent, "Throw In Direction", coachName).getSum();

            Direction throwInDirection;
            if (lastValidSquare.getX() == 0) { // West edge
                if (throwInTemplate == 1 || throwInTemplate == 2) {
                    throwInDirection = Direction.NE;
                } else if (throwInTemplate == 3 || throwInTemplate == 4) {
                    throwInDirection = Direction.E;
                } else {
                    throwInDirection = Direction.SE;
                }
            } else if (lastValidSquare.getX() == eventManager.getArena().getPitch().getWidth() - 1) { // East edge
                if (throwInTemplate == 1 || throwInTemplate == 2) {
                    throwInDirection = Direction.SW;
                } else if (throwInTemplate == 3 || throwInTemplate == 4) {
                    throwInDirection = Direction.W;
                } else {
                    throwInDirection = Direction.NW;
                }
            } else if (lastValidSquare.getY() == 0) { // North edge
                if (throwInTemplate == 1 || throwInTemplate == 2) {
                    throwInDirection = Direction.SE;
                } else if (throwInTemplate == 3 || throwInTemplate == 4) {
                    throwInDirection = Direction.S;
                } else {
                    throwInDirection = Direction.SW;
                }
            } else /*if (lastValidSquare.getY() == eventManager.getArena().getPitch().getHeight() - 1)*/ { // South edge
                if (throwInTemplate == 1 || throwInTemplate == 2) {
                    throwInDirection = Direction.NW;
                } else if (throwInTemplate == 3 || throwInTemplate == 4) {
                    throwInDirection = Direction.N;
                } else {
                    throwInDirection = Direction.NE;
                }
            }
            throwInBallEvent.setDirection(throwInDirection);

            Integer throwInDistance = Roll.roll(2, D6, throwInBallEvent, "Throw In Distance", coachName).getSum();
            throwInBallEvent.setDistance(throwInDistance);
            destination = roulette(eventManager, throwInBallEvent, coachName);
        } else {
            Square square = eventManager.getArena().getPitch().getSquare(lastValidSquare);
            if (square.hasPlayer()) {
                boolean catched = false;
                // TODO catch roll
                if (!catched) {
                    ScatterBallEvent failedCatch = scatterBall(scatterBallEvent, square, coachName);
                    destination = roulette(eventManager, failedCatch, coachName);
                }
            }
        }
        return destination;
    }

    private ScatterBallEvent scatterBall(ScatterBallEvent scatterBallEvent, Square square, String coachName) {
        ScatterBallEvent event = new ScatterBallEvent(scatterBallEvent.getBallId());
        Player player = square.getPlayer();
        Direction direction = Direction.getDirection(Roll.roll(1, DS, event, "Scatter", player != null ? player.toString() : coachName).getSum());
        event.setDirection(direction);
        event.setDistance(1);
        event.setType(BallMove.BallMoveType.SCATTER);
        return event;
    }

    private Choice checkChoice(Choice choice) {
        Choice res = null;
        if (choice instanceof Concede) {
            // TODO Concede the match
        } else {
            res = choice;
        }
        return res;
    }

}
