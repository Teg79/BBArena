package bbarena.model.team

import java.io.Serializable
import java.util.*

data class Team(val id: Long, val name:String, val roster: Roster, val players: MutableSet<Player> = TreeSet(PlayerComparator()), val coach: TeamCoach, var treasury: Long = 0, var pettyCash: Long = 0, var reRolls: Int = 0, var fanFactor: Int = 0, var assistants: Int = 0, var cheerleaders: Int = 0, var apothecary: Boolean = false) : Serializable {

    // TODO: move to bbarena.model.ScoreBoard
    var isApoUsed = false

    // TODO: move to bbarena.model.ScoreBoard
    var wizard = 0

    val teamRating: Int
        get() {
            var res = Math.floor(((totalCost + treasury) / 10000).toDouble()).toInt()
            res += Math.floor((totalSpp / 5).toDouble()).toInt()
            return res
        }

    val teamValue: Long
        get() = teamCost + pettyCash + players.sumBy { player -> player.cost.toInt() }

    val totalSpp: Int
        get() = players.sumBy { player -> player.experience.spp + player.experience.old }

    /**
     * Calculates the sum of all players, rerolls, FF, AC, CL and Apo. No
     * Treasury!
     *
     * @return Total Cost of the Team
     */
    val totalCost: Long
        get() = teamCost + players.sumBy { player -> player.cost.toInt() }

    private val teamCost: Long
        get() {
            var res: Long = 0
            res += reRolls * reRollCost
            res += (fanFactor * 10000).toLong()
            res += (assistants * 10000).toLong()
            res += (cheerleaders * 10000).toLong()
            if (apothecary) {
                res += 50000
            }
            return res
        }

    /**
     * Getter for property race.
     *
     * @return Value of property race.
     */
    val race: String
        get() = roster.race

    /**
     * Getter for property reRollsCost.
     *
     * @return Value of property reRollsCost.
     */
    val reRollCost: Long
        get() = this.roster.rerollCost

    val teamInfo: TeamInfo
        get() = TeamInfo(id, name, coach.name, roster.race, teamValue)

    /**
     * Overwrite the player with the same number o add the player if his
     * position is free
     *
     * @param player
     * Player to set
     * @return The old player or null if the position was free
     */
    fun setPlayer(player: Player): Player? {
        var done = false
        var res: Player? = null

        for (p in players) {
            if (p.num == player.num) {
                player.team = this
                players.remove(p)
                res = p
                players.add(player)
                done = true
                break
            }
        }
        if (!done) {
            addPlayer(player)
            res = null
        }

        return res
    }

    fun addPlayer(player: Player) {
        player.team = this
        players.add(player)
    }

    fun removePlayer(num: Int): Player? {
        var res: Player? = null

        for (p in players) {
            if (p.num == num) {
                res = p
                players.remove(p)
                p.team = null
                break
            }
        }
        return res
    }

    fun getPlayer(num: Int): Player? {
        for (p in players) {
            if (p.num == num) {
                return p
            }
        }

        return null
    }

    fun transferGoldToPettyCash(gold: Long) {
        this.treasury -= gold
        this.pettyCash += gold
    }

}
