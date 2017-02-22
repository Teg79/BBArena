package bbarena.model;

import java.util.Set;

public interface ChoiceFilter {

    Set<Choice> filter(Set<Choice> choices);

}
