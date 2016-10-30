package net.sf.bbarena.model.pitch;

import net.sf.bbarena.model.Coordinate;

/**
 * Created by teg on 30/10/16.
 */
public class SquareDestination {

    private final Coordinate _lastValidSquare;
    private final boolean _outOfPitch;

    public SquareDestination(Coordinate lastValidSquare, boolean outOfPitch) {
        _lastValidSquare = lastValidSquare;
        _outOfPitch = outOfPitch;
    }

    public Coordinate getLastValidSquare() {
        return _lastValidSquare;
    }

    public boolean isOutOfPitch() {
        return _outOfPitch;
    }
}
