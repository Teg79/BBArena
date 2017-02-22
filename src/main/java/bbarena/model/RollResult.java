package bbarena.model;

import bbarena.model.event.RollModifier;
import bbarena.model.team.AttributeModifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RollResult {

    private final Roll _roll;
    private final int[] _results;
    private final String _why;
    private final String _who;
    private final List<RollModifier> _modifiers;
    private AttributeModifier _attribute = null;
    private Integer _target = null;

    protected RollResult(Roll roll, int[] results, String why, String who) {
        _roll = roll;
        _results = results;
        _why = why;
        _who = who;
        _modifiers = new ArrayList<>();
    }

    public List<RollModifier> getModifiers() {
        return _modifiers;
    }

    public RollResult addModifier(int modifier, String description) {
        addModifier(new RollModifier(modifier, description));
        return this;
    }

    public RollResult addModifier(RollModifier modifier) {
        _modifiers.add(modifier);
        return this;
    }

    public RollResult setAttribute(AttributeModifier attribute) {
        _attribute = attribute;
        return this;
    }

    public RollResult setTarget(Integer target) {
        _target = target;
        return this;
    }

    public Roll getRoll() {
        return _roll;
    }

    public int[] getResults() {
        return _results;
    }

    public Integer getSum() {
        Integer res = Arrays.stream(_results).sum();
        res += _modifiers.stream().mapToInt(value -> value.getModifier()).sum();
        if (_attribute != null) {
            res += _attribute.getMod();
        }
        if (_target != null) {
            res -= _target;
        }
        return res;
    }

    public String getWhy() {
        return _why;
    }

    public String getWho() {
        return _who;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("Roll [").append(_why).append("] ")
                .append(_roll.getDice()).append(_roll.getType())
                .append(" ")
                .append(Arrays.toString(_results));
        if (_modifiers.size() > 0) {
            builder.append(" + ")
                    .append(_modifiers.toString());
        }
        if (_attribute != null) {
            builder.append(" + ").append(_attribute.getMod()).append(" ").append(_attribute.getType());
        }
        if (_target != null) {
            builder.append(" - ").append(_target).append(" Target");
        }
        builder.append(" = ")
                .append(getSum());
        return builder.toString();
    }
}
