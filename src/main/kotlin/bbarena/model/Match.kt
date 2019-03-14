package bbarena.model

import bbarena.model.event.EventManager
import bbarena.model.pitch.Pitch
import java.util.*

// TODO: Move to another verticle
private val matches = mutableMapOf<String, Match<*>>()

fun getMatch(matchId: String): Match<*>? {
    return matches[matchId]
}

val matchIds: Set<String>
    get() = matches.keys

class Match<R : RuleSet>(val matchId: String = UUID.randomUUID().toString(), pitchFactory: () -> Pitch, val coaches: List<Coach>, val source: R) {

    val arena: Arena
    val eventManager: EventManager

    var currentDrive: Int = 1

    var status: Status = Status.STARTING
    var start: Date? = null
    var end: Date? = null

    val matchInfo: MatchInfo
        get() {
            val res = MatchInfo()

            res.end = end
            res.matchId = matchId
            res.start = start
            res.status = status
            res.scoreBoards = arena.scoreBoards

            return res
        }

    enum class Status {
        STARTING, PLAYING, ENDING, FINISHED
    }

    init {
        val teams = coaches.map { coach -> coach.team }
        arena = Arena(this, teams, pitchFactory)
        eventManager = EventManager(arena)
        matches[matchId] = this
    }

    fun start() {
        this.source.start(eventManager, coaches)
    }

}
