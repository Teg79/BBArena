package bbarena.server;

import net.sf.bbarena.model.dice.DieRandomizer;
import net.sf.bbarena.model.dice.RandomOrgRandomizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by teg on 06/11/16.
 */
public class SimGenerator implements DieRandomizer {

    private final DieRandomizer _default = RandomOrgRandomizer.getInstance();
    private final List<Integer> _rolls;
    private int _rollCounter = 0;

    public SimGenerator(Integer... rolls) {
        _rolls = new ArrayList<>(Arrays.asList(rolls));
    }

    public void addRoll(Integer roll) {
        _rolls.add(roll);
    }

    @Override
    public int getRollFace(int faces, String rollId) {
        Integer roll = null;

        if (_rolls.size() > _rollCounter) {
            roll = _rolls.get(_rollCounter++);
        }
        if (roll == null) {
            roll = _default.getRollFace(faces, rollId);
        }

        return roll;
    }
}
