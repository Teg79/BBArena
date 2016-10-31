package net.sf.bbarena.model.pitch;

import net.sf.bbarena.model.Coordinate;

/**
 * Created by teg on 30/10/16.
 */
public class SquareDestination {

    private final Coordinate _lastValidSquare;
    private final boolean _outOfPitch;
    private final Integer _effectiveDistance;

    public SquareDestination(Coordinate lastValidSquare, boolean outOfPitch, Integer effectiveDistance) {
        _lastValidSquare = lastValidSquare;
        _outOfPitch = outOfPitch;
        _effectiveDistance = effectiveDistance;
    }

    public Integer getEffectiveDistance() {
        return _effectiveDistance;
    }

    public Coordinate getLastValidSquare() {
        return _lastValidSquare;
    }

    public boolean isOutOfPitch() {
        return _outOfPitch;
    }
}
