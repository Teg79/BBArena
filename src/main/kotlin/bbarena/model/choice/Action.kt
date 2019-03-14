package bbarena.model.choice

import bbarena.model.Choice

/**
 * Created by Teg on 03/12/2016.
 */
enum class Action(val isSingleUse: Boolean) : Choice {
    MOVE(java.lang.Boolean.FALSE),
    BLOCK(java.lang.Boolean.FALSE),
    BLITZ(java.lang.Boolean.TRUE),
    FOUL(java.lang.Boolean.TRUE),
    PASS(java.lang.Boolean.TRUE),
    HANDOFF(java.lang.Boolean.TRUE)
}
