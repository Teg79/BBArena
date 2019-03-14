package bbarena.model.pitch;

import bbarena.model.Choice;
import bbarena.model.Coordinate;

import java.util.HashMap;
import java.util.Map;

public class TeamSetUp implements Choice {

    private final Map<Long, Coordinate> _playersPlacement;

    public TeamSetUp() {
        _playersPlacement = new HashMap<>();
    }

    public void placePlayer(long playerId, Integer x, Integer y) {
        _playersPlacement.put(playerId, new Coordinate(x, y));
    }

    public Map<Long, Coordinate> getSetUp() {
        return _playersPlacement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
