package bbarena.model

import bbarena.model.event.RollModifier
import bbarena.model.team.AttributeModifier

data class RollResult(val roll: Roll, val results: List<Int>, val why: String, val who: String, val modifiers: MutableList<RollModifier> = mutableListOf(), val attribute: AttributeModifier? = null, val target: Int = 0) {

    val sum: Int
        get() {
            var res: Int = results.sum()
            res += this.modifiers.stream().mapToInt { value -> value.modifier }.sum()
            res += attribute?.mod ?: 0
            res -= target
            return res
        }

    fun addModifier(modifier: Int, description: String): RollResult {
        addModifier(RollModifier(modifier, description))
        return this
    }

    fun addModifier(modifier: RollModifier): RollResult {
        this.modifiers.add(modifier)
        return this
    }

    override fun toString(): String {
        val builder = StringBuilder("Roll [").append(why).append("] ")
                .append(roll.dice).append(roll.type)
                .append(" ")
                .append(results.joinToString())
        if (this.modifiers.size > 0) {
            builder.append(" + ")
                    .append(this.modifiers.toString())
        }
        if (attribute != null) {
            builder.append(" + ").append(attribute.mod).append(" ").append(attribute.attribute)
        }
        builder.append(" - ").append(target).append(" Target")
        builder.append(" = ")
                .append(sum)
        return builder.toString()
    }
}
