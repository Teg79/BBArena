package bbarena.model

import java.io.Serializable
import java.util.*

data class ScoreBoard(var score: Int = 0, var cas: Int = 0, var fame: Int = 0, var fans: Int = 0) : Observable(), Serializable {

    fun addScore() {
        score++
    }

    fun addCas() {
        cas++
    }
}
