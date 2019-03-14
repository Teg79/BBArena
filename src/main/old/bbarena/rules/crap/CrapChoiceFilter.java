package bbarena.rules.crap;

import bbarena.model.*;

import java.util.LinkedHashSet;
import java.util.Set;

public class CrapChoiceFilter implements ChoiceFilter {

    private final Arena _arena;

    public CrapChoiceFilter(Arena arena) {
        _arena = arena;
    }

    @Override
    public Set<Choice> filter(Set<Choice> choices) {
        Set<Choice> res;
        if (Weather.Companion.getWeather(Weather.WeatherType.BLIZZARD).equals(_arena.getWeather())) {
            res = new LinkedHashSet<>();
            choices.stream().forEach(crapChoice -> {
                if (!crapChoice.equals(RangeRuler.Range.BOMB) && !crapChoice.equals(RangeRuler.Range.LONG)) {
                    res.add(crapChoice);
                }
            });
        } else {
            res = choices;
        }
        return res;
    }
}
