package bbarena.model.rules.bb

import bbarena.model.pitch.Dugout
import bbarena.model.pitch.DugoutRoom
import bbarena.model.team.Team

/**
 * BBDugout is the Dugout implementation for the classic BB Pitch
 *
 */

enum class BBDugoutRoom : DugoutRoom {
    RESERVES, KO, BH, SI, RIP;

    override fun getRoomName(): String = name

}

fun buildBBDugout(team: Team) = Dugout(team, BBDugoutRoom.values(), BBDugoutRoom.RESERVES)

