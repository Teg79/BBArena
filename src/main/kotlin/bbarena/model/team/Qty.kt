package bbarena.model.team

import java.io.Serializable

data class Qty(val min: Int, val max: Int) : Serializable {

    override fun toString(): String {
        return min.toString() + "-" + max
    }

}