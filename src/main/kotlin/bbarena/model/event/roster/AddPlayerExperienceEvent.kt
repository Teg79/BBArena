package bbarena.model.event.roster

import bbarena.model.Arena
import bbarena.model.team.Experience
import bbarena.model.team.Player
import bbarena.model.util.Concat

class AddPlayerExperienceEvent(playerId: Long, val comp: Int, val td: Int, val inter: Int, val cas: Int, val mvp: Int, val old: Int, val scars: Int, val fouls: Int, val rush: Int) : RosterEvent(playerId) {

    private var player: Player? = null
    var newExperience: Experience = Experience(comp, td, inter, cas, mvp, old, scars, fouls, rush)

    override fun doEvent(arena: Arena) {
        player = getPlayer(arena)
        val p = player
        if (p != null) {
            arena.playerManager.addPlayerExperience(p, newExperience)
        }
    }

    override fun undoEvent(arena: Arena) {
        val p = player
        if (p != null) {
            arena.playerManager.subPlayerExperience(p, newExperience)
        }
    }

    override val string: String
        get() = Concat.buildLog(javaClass,
                Pair("playerId", playerId),
                Pair("exp", newExperience))

}
