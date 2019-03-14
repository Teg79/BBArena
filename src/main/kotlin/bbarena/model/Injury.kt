package bbarena.model

import java.io.Serializable
import java.util.*

const val INJ_TYPE_SEPARATOR = "+"

const val INJ_TOKEN_SEPARATOR = " "

const val INJ_SEPARATOR = ";"

fun parseInjury(inj: String): List<Injury> {
    val injList = ArrayList<Injury>()

    val injTok = StringTokenizer(inj, INJ_SEPARATOR)
    while (injTok.hasMoreTokens()) {
        val nextInj = injTok.nextToken().trim { it <= ' ' }
        if (nextInj.isNotEmpty()) {
            val newInj = buildInjury(nextInj)
            injList.add(newInj)
        }
    }

    return injList
}

fun buildInjury(inj: String): Injury {
    val it = inj.trim()

    val tok = StringTokenizer(it, INJ_TOKEN_SEPARATOR)

    // Types
    val type = mutableListOf<InjuryType>()
    val types = tok.nextToken()
    val typeTok = StringTokenizer(types, INJ_TYPE_SEPARATOR)
    while (typeTok.hasMoreTokens()) {
        type.add(InjuryType.valueOf(typeTok.nextToken().toUpperCase()))
    }

    // Causes
    var isHealed = true
    var cause = InjuryCause.Block
    if (tok.hasMoreTokens()) {
        val token2 = tok.nextToken()
        if (InjuryCure.isHealed(token2)) {
            isHealed = true
        } else {
            cause = InjuryCause.valueOf(token2)

            if (tok.hasMoreTokens() && InjuryCure.isHealed(tok.nextToken())) {
                isHealed = true
            }
        }
    }

    if (type.size > 0 && (type[0] == InjuryType.AC || type[0] == InjuryType.RETIRED)) {
        cause = InjuryCause.Other
    }
    return Injury(cause = cause, type = type, isHealed = isHealed)
}

enum class InjuryType {
    BH, MNG, N, SI, DEATH, AC, RETIRED;

    fun combine(vararg types: InjuryType) =
            types.joinToString(INJ_TYPE_SEPARATOR)
}

enum class InjuryCause {
    Block, Movement, Foul, Crowd, Handicap, Event, Ageing, Wizard, Potion, Other
}

enum class InjuryCure {
    Healed, Regenerated, Potion, None;

    override fun toString(): String {
        var res: String? = null
        if (this == Potion) {
            res = "Healed with Potion"
        } else {
            res = super.toString()
        }
        return res
    }

    companion object {

        fun isHealed(cure: String): Boolean {
            var res = false
            for (c in values()) {
                if (c.name.equals(cure, ignoreCase = true)) {
                    res = true
                    break
                }
            }
            return res
        }
    }
}

data class Injury(val cause: InjuryCause = InjuryCause.Block, val cure: InjuryCure = InjuryCure.None, private val type: MutableList<InjuryType> = mutableListOf(), var isHealed: Boolean = false) : Serializable {

    override fun toString(): String {
        return (getType() + INJ_TOKEN_SEPARATOR + getCause()
                + if (isHealed) getCure() else "")
    }

    /**
     * Getter for property type.
     *
     * @return Value of property type.
     */
    fun getType(): String {
        val res = StringBuffer()
        val it = type.iterator()
        while (it.hasNext()) {
            val t = it.next()
            res.append(t.toString() + if (it.hasNext()) INJ_TYPE_SEPARATOR else "")
        }
        return res.toString()
    }

    /**
     * Getter for property cause.
     *
     * @return Value of property cause.
     */
    fun getCause(): String? {
        var res: String? = null

        if (cause == null) {
            res = ""
        } else {
            res = cause!!.toString()
        }

        return res
    }

    /**
     * @return Returns the cure.
     */
    fun getCure(): String {
        return cure.toString()
    }

    operator fun contains(type: InjuryType): Boolean {
        var res = false
        for (t in this.type) {
            if (t == type) {
                res = true
                break
            }
        }
        return res
    }

    companion object {

        private const val serialVersionUID = 5575934750045433839L

    }

}