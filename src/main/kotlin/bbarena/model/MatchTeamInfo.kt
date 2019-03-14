package bbarena.model

import bbarena.model.team.TeamInfo

import java.io.Serializable

class MatchTeamInfo @JvmOverloads constructor(team: TeamInfo, score: Int = 0) : TeamInfo(team.id, team.name, team.coach, team.race, team.teamValue), Serializable {

    var score = 0

    init {

        this.score = score
    }

    fun addTD() {
        this.score++
    }

    fun addTD(tds: Int) {
        for (i in 0 until tds) {
            addTD()
        }
    }

    companion object {

        private const val serialVersionUID = -5647762138958951271L
    }

}