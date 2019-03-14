package bbarena.model

import bbarena.model.team.Team
import java.io.Serializable
import java.util.*

data class Referee(val teams: List<Team>) : Observable(), Serializable {

    private val teamStatus = mutableMapOf<Team, RefereeStatus>()

    enum class RefereeStatus {
        NORMAL, BONDED, WATCHING
    }

    init {
        for (team in teams) {
            teamStatus[team] = RefereeStatus.NORMAL
        }
    }

    fun setStatus(team: Team, status: RefereeStatus) {
        teamStatus[team] = status
        setChanged()
        notifyObservers()
    }

    fun getStatus(team: Team): RefereeStatus? {
        return teamStatus[team]
    }

}
