package net.sf.bbarena.rules.crap;

import net.sf.bbarena.model.Coach;
import net.sf.bbarena.model.Roll;
import net.sf.bbarena.model.RollResult;
import net.sf.bbarena.model.event.EventManager;
import net.sf.bbarena.model.event.game.CatchBallEvent;
import net.sf.bbarena.model.event.game.ScatterBallEvent;
import net.sf.bbarena.model.pitch.Ball;
import net.sf.bbarena.model.pitch.Pitch;
import net.sf.bbarena.model.pitch.Square;
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

        CatchBallEvent catchBallEvent = new CatchBallEvent(ball.getId());
        catchBallEvent.setBall(ball);
        catchBallEvent.setPlayer(player);

        int tzCount = pitch.getOpponentTZCount(square.getCoords(), player.getTeam());
        // pitch.getPlayersInRange()
        // TODO Add Foul Appearance
        // TODO Add Catch Skill

        RollResult roll = Roll.roll(1, D6, catchBallEvent, "Catch", player.toString());
        roll.addModifier(tzCount, "Takle Zone");
        if (accurate) {
            roll.addModifier(1, "Accurate Pass");
        }

        boolean catched = player.getAg() + roll.getSum() >= TARGET;
        catchBallEvent.setFailed(catched);
        eventManager.forward(catchBallEvent);

        return catched;
    }

}
