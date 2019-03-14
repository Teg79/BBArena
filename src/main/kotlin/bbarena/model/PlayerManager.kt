package bbarena.model

import bbarena.model.exception.PlayerNotFoundException
import bbarena.model.team.Experience
import bbarena.model.team.Player
import java.io.Serializable

data class PlayerManager(val arena: Arena) : Serializable {

    var selectedPlayer: Player? = null

    fun addPlayerExperience(player: Player, exp: Experience) {
        val e = player.newExperience
        e.add(exp)
    }

    fun subPlayerExperience(player: Player, exp: Experience) {
        val e = player.newExperience
        e.sub(exp)
    }

    fun getPlayer(playerId: Long): Player {
        arena.teams.forEach{ team -> team.players.forEach { player -> if (player.id == playerId) return player } }
        throw PlayerNotFoundException(playerId)
    }

}
