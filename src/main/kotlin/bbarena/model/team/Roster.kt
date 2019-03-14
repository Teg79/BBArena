package bbarena.model.team

import java.io.Serializable

data class Roster(val race: String, val description: String, val minRerolls: Int, val maxRerolls: Int, val rerollCost: Long) : Serializable {

    val players = mutableListOf<PlayerTemplate>()
    val starsId = mutableListOf<String>()
    val rerolls = Qty(minRerolls, maxRerolls)

    fun addPlayer(player: PlayerTemplate): Boolean {
        return players.add(player)
    }

    fun addStarId(starId: String): Boolean {
        return starsId.add(starId)
    }

}
