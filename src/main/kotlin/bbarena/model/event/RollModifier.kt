package bbarena.model.event

import java.io.Serializable

class RollModifier(val modifier: Int, val description: String) : Serializable {

    override fun toString(): String {
        return StringBuilder().append(modifier).append(" ").append(description).toString()
    }

    companion object {

        private const val serialVersionUID = -4692545546332381896L
    }

}
