package bbarena.model

import bbarena.model.dice.DieRandomizer
import bbarena.model.dice.RandomOrgRandomizer
import bbarena.model.event.Die
import bbarena.model.event.Event
import mu.KotlinLogging
import java.util.*

private val logger = KotlinLogging.logger {}

var dieRandomizer: DieRandomizer = RandomOrgRandomizer

fun roll(dice: Int, type: Die, event: Event, why: String, who: String): RollResult {
    val roll = Roll(dice, type, dieRandomizer)
    val results = roll.results
    val rollResult = RollResult(roll, results, why, who)
    event.addDiceRoll(rollResult)
    return rollResult
}

data class Roll(val dice: Int, val type: Die, val generator: DieRandomizer) {

    val id: String = UUID.randomUUID().toString()
    val results = mutableListOf<Int>()

    init {
        for (i in 0 until this.dice) {
            var resultAggregation: Int? = 0
            for (j in this.type.faces.size - 1 downTo 0) {
                val die = this.type.faces[j]
                val result = generator.getRollFace(die, id)
                logger.debug("Roll " + id + ": " + result + "/" + type.faces[j])
                resultAggregation = resultAggregation!! + result * Math.pow(10.0, (this.type.faces.size - 1 - j).toDouble()).toInt()
            }
            results[i] = resultAggregation!!
        }
    }

}
