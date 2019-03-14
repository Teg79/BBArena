package bbarena.model.event.game;

/**
 * Created by Teg on 02/11/16.
 */
public class ThrowInBallEvent extends ScatterBallEvent {

    public ThrowInBallEvent(int ballId) {
        super(ballId);
    }

    public ThrowInBallEvent(int ballId, int direction) {
        super(ballId, direction);
    }

    public ThrowInBallEvent(int ballId, int direction, int distance) {
        super(ballId, direction, distance);
    }
}
