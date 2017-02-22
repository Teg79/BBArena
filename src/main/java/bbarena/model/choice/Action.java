package bbarena.model.choice;

import bbarena.model.Choice;

/**
 * Created by Teg on 03/12/2016.
 */
public enum Action implements Choice {
    MOVE(Boolean.FALSE),
    BLOCK(Boolean.FALSE),
    BLITZ(Boolean.TRUE),
    FOUL(Boolean.TRUE),
    PASS(Boolean.TRUE),
    HANDOFF(Boolean.TRUE);

    Action(Boolean singleUse) {
        _singleUse = singleUse;
    }

    private final Boolean _singleUse;

    public Boolean isSingleUse() {
        return _singleUse;
    }
}
