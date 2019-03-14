package bbarena.model

import java.io.Serializable
import java.util.*

class MatchInfo : Serializable {

    var matchId: String? = null

    var start: Date? = null

    var end: Date? = null

    var status: Match.Status? = null

    var scoreBoards: List<ScoreBoard>? = null

    companion object {

        private const val serialVersionUID = -4539363712532167223L
    }
}
