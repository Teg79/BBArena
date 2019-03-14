package bbarena.model.event.game

import bbarena.model.Arena

class FameEvent(private val fameByCoach: IntArray) : GameEvent() {

    val oldFameByCoach = mutableListOf<Int>()

    override val string: String
        get() = {
            val res = StringBuilder()
            res.append(javaClass.simpleName)
                    .append("[")
            fameByCoach.forEachIndexed { coach, fame ->
                res.append("fame")
                        .append(coach)
                        .append(": ")
                        .append(fame)
                        .append(", ") }
            res.append("]")
            res.toString()
        }.invoke()

    fun setFameCoach(coach: Int, fame: Int) {
        fameByCoach[coach] = fame
    }

    override fun doEvent(arena: Arena) {
        fameByCoach.forEachIndexed { coach, fame ->
            oldFameByCoach[coach] = arena.getScoreBoard(coach).fame!!
            arena.getScoreBoard(coach).fame = fame
        }
    }

    override fun undoEvent(arena: Arena) {
        fameByCoach
    }
}
