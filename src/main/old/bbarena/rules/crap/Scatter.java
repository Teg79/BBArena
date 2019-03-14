package bbarena.rules.crap;

import bbarena.model.Coach;
import bbarena.model.Coordinate;
import bbarena.model.Direction;
import bbarena.model.Roll;
import bbarena.model.event.EventManager;
import bbarena.model.event.game.ScatterBallEvent;
import bbarena.model.event.game.ThrowInBallEvent;
import bbarena.model.pitch.BallMove;
import bbarena.model.pitch.Square;
import bbarena.model.pitch.SquareDestination;
import bbarena.model.team.Player;

import static bbarena.model.event.Die.D6;
import static bbarena.model.event.Die.DS;

/**
 * Created by Teg on 04/11/2016.
 */
public class Scatter {

    public static SquareDestination bounce(EventManager eventManager, Integer ballId, Square square, Coach coach, boolean last) {
        return bounce(eventManager, buildScatterBallEvent(ballId, square, coach), coach, false, last);
    }

    public static SquareDestination bounce(EventManager eventManager, Integer ballId, Square square, Coach coach) {
        return bounce(eventManager, buildScatterBallEvent(ballId, square, coach), coach);
    }

    public static SquareDestination bounce(EventManager eventManager, ScatterBallEvent scatterBallEvent, Coach coach) {
        return bounce(eventManager, scatterBallEvent, coach, false);
    }

    public static SquareDestination bounce(EventManager eventManager, ScatterBallEvent scatterBallEvent, Coach coach, boolean kickOff) {
        return bounce(eventManager, scatterBallEvent, coach, kickOff, false);
    }

    public static SquareDestination bounce(EventManager eventManager, ScatterBallEvent scatterBallEvent, Coach coach, boolean kickOff, boolean last) {
        eventManager.forward(scatterBallEvent);
        SquareDestination destination = scatterBallEvent.getDestination();
        Coordinate lastValidSquare = destination.getLastValidSquare();

        Square lastSquare = eventManager.getArena().getPitch().getSquare(lastValidSquare);
        if (kickOff && (destination.isOutOfPitch() || lastSquare == null || lastSquare.getTeamOwner().equals(coach.getTeam()))) {

            destination = null; // touchback

        } else {
            boolean catched = false;

            if (destination.isOutOfPitch()) {
                ThrowInBallEvent throwInBallEvent = new ThrowInBallEvent(scatterBallEvent.getBallId());
                Integer throwInTemplate = Roll.Companion.roll(1, D6, throwInBallEvent, "Throw In Direction", coach.getName()).getSum();

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

                Integer throwInDistance = Roll.Companion.roll(2, D6, throwInBallEvent, "Throw In Distance", coach.getName()).getSum();
                throwInBallEvent.setDistance(throwInDistance - 1);
                destination = bounce(eventManager, throwInBallEvent, coach);
            } else {
                if (!kickOff && lastSquare.hasPlayer()) {
                    catched = AgilityTable.catchRoll(eventManager, coach);
                }
            }
            if (!catched && !last && !kickOff) {
                ScatterBallEvent failedCatch = buildScatterBallEvent(scatterBallEvent.getBallId(), lastSquare, coach);
                destination = bounce(eventManager, failedCatch, coach, kickOff, true);
            }
        }
        return destination;
    }

    public static ScatterBallEvent buildScatterBallEvent(Integer ballId, Square square, Coach coach) {
        ScatterBallEvent event = new ScatterBallEvent(ballId);
        Player player = square.getPlayer();
        Direction direction = Direction.Companion.getDirection(
            Roll.Companion.roll(1, DS, event, "Scatter", player != null ? player.toString() : coach.getName()).getSum());
        event.setDirection(direction);
        event.setDistance(1);
        event.setType(BallMove.BallMoveType.SCATTER);
        return event;
    }

}
