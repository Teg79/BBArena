package net.sf.bbarena.model.choice;

import net.sf.bbarena.model.Choice;

public class Continue implements Choice {

    @Override
    public int hashCode() {
        return Continue.class.getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Continue;
    }
}
