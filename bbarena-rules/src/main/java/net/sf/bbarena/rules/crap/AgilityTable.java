package net.sf.bbarena.rules.crap;

import net.sf.bbarena.model.*;
import net.sf.bbarena.model.event.EventManager;
import net.sf.bbarena.model.event.game.CatchBallEvent;
import net.sf.bbarena.model.event.game.MovePlayerEvent;
import net.sf.bbarena.model.event.game.PickUpBallEvent;
import net.sf.bbarena.model.pitch.Ball;
import net.sf.bbarena.model.pitch.Pitch;
import net.sf.bbarena.model.pitch.Square;
import net.sf.bbarena.model.team.AttributeModifier;
import net.sf.bbarena.model.team.Attributes;
import net.sf.bbarena.model.team.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.sf.bbarena.model.event.Die.D6;

/**
 * Created by Teg on 04/11/2016.
 */
public class AgilityTable {

    private static final Logger _log = LoggerFactory.getLogger(AgilityTable.class);

    public static final Integer TARGET = 7;

    public static boolean dodgeRoll(EventManager eventManager, Coach coach, Player player, Direction direction) {
        return dodgeRoll(eventManager, coach, player, direction, false);
    }

    public static boolean dodgeRoll(EventManager eventManager, Coach coach, Player player, Direction direction, boolean gfi) {
        boolean dodged = true;
        // TODO: Shadowing, Tentacles, Dodge, Tackle...

        Pitch pitch = eventManager.getArena().getPitch();
        Coordinate from = player.getSquare().getCoords();

        MovePlayerEvent movePlayerEvent = new MovePlayerEvent(player.getId(), direction);
        if (gfi) {
            int gfiRoll = Roll.roll(1, D6, movePlayerEvent, "GFI", player.toString()).setTarget(2).getSum();
            if (gfiRoll < 0) {
                dodged = false;
            }
        }

        if (dodged) {
            int opponentTZCount = pitch.getOpponentTZCount(from, player.getTeam());
            if (opponentTZCount > 0) {
                RollResult dodge = Roll.roll(1, D6, movePlayerEvent, "Dodge", player.toString());
                dodge.addModifier(1, "Dodge Roll");
                dodge.addModifier(-pitch.getOpponentTZCount(from.getNext(direction), player.getTeam()), "Tackle Zone");
                dodge.setAttribute(new AttributeModifier(Attributes.Attribute.AG, player.getAg()));
                dodge.setTarget(TARGET);

                int result = dodge.getResults()[0];
                if (result == 6) {
                    dodged = true;
                } else
                    dodged = result != 1 && dodge.getSum() >= 0;
            }
        }
        eventManager.forward(movePlayerEvent);
        if (!dodged) {
            ArmorTable.armorRoll(eventManager, coach, player);
        }
        return dodged;
    }

    public static boolean catchRoll(EventManager eventManager, Coach coach) {
        return catchRoll(eventManager, coach, false);
    }

    public static boolean catchRoll(EventManager eventManager, Coach coach, boolean accurate) {
        Pitch pitch = eventManager.getArena().getPitch();
        Ball ball = pitch.getBall();
        Square square = ball.getSquare();
        Player player = square.getPlayer();
        if (player == null) {
            String msg = "No player on the ball square!";
            _log.error(msg);
            throw new IllegalArgumentException(msg);
        }

        CatchBallEvent catchBallEvent = new CatchBallEvent(ball.getId(), player.getId());

        int tzCount = pitch.getOpponentTZCount(square.getCoords(), player.getTeam());
        // pitch.getPlayersInRange()
        // TODO Add Foul Appearance
        // TODO Add Catch Skill

        RollResult roll = Roll.roll(1, D6, catchBallEvent, "Catch", player.toString());
        roll.addModifier(-tzCount, "Takle Zone");
        if (accurate) {
            roll.addModifier(1, "Accurate Pass");
        }
        roll.setAttribute(new AttributeModifier(Attributes.Attribute.AG, player.getAg()));
        roll.setTarget(TARGET);

        int result = roll.getResults()[0];
        boolean catched;
        if (result == 6) {
            catched = true;
        } else
            catched = result != 1 && roll.getSum() >= 0;
        catchBallEvent.setFailed(!catched);
        eventManager.forward(catchBallEvent);

        return catched;
    }

    public static boolean pickupRoll(EventManager eventManager, Coach coach) {
        Pitch pitch = eventManager.getArena().getPitch();
        Ball ball = pitch.getBall();
        Square square = ball.getSquare();
        Player player = square.getPlayer();
        if (player == null) {
            String msg = "No player on the ball square!";
            _log.error(msg);
            throw new IllegalArgumentException(msg);
        }

        PickUpBallEvent pickUpBallEvent = new PickUpBallEvent(ball.getId(), player.getId());

        int tzCount = pitch.getOpponentTZCount(square.getCoords(), player.getTeam());
        // TODO Add Sure Hands Skill

        RollResult roll = Roll.roll(1, D6, pickUpBallEvent, "PickUp", player.toString());
        roll.addModifier(1, "PickUp Roll");
        roll.addModifier(-tzCount, "Takle Zone");
        roll.setAttribute(new AttributeModifier(Attributes.Attribute.AG, player.getAg()));
        roll.setTarget(TARGET);

        int result = roll.getResults()[0];
        boolean pickedUp;
        if (result == 6) {
            pickedUp = true;
        } else
            pickedUp = result != 1 && roll.getSum() >= 0;
        pickUpBallEvent.setFailed(!pickedUp);
        eventManager.forward(pickUpBallEvent);

        return pickedUp;
    }

}
