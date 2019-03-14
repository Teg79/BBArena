package bbarena.model.choice

import bbarena.model.Choice

class Continue : Choice {

    override fun hashCode(): Int {
        return Continue::class.java.name.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is Continue
    }
}
