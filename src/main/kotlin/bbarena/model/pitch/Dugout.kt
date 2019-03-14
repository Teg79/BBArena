package bbarena.model.pitch

import bbarena.model.exception.DugoutException
import bbarena.model.team.Player
import bbarena.model.team.Team
import java.io.Serializable
import java.util.Observable
import kotlin.NoSuchElementException


/**
 * This interface must be implemented with an Enum to represent the possible
 * Dogout Rooms for a Pitch implementation.
 *
 */
interface DugoutRoom : Serializable {

    fun getRoomName() : String

}

/**
 * This class is the base implementation for a Dugout and must be extended for
 * each Pitch implementation
 *
 */
class Dugout(val team: Team, private val rooms: Array<out DugoutRoom>, startRoom: DugoutRoom) : Observable(), Serializable {

    private val dugoutMap: MutableMap<DugoutRoom, MutableList<Player>> = mutableMapOf()

    init {
        rooms.forEach { room ->
            dugoutMap[room] = mutableListOf()
        }
        team.players.forEach { player ->
            addPlayer(startRoom, player)
        }
    }

    /**
     * Return the list of Players in a Room
     *
     * @param room
     * The DugoutRoom
     * @return The List of the Player in the Room
     */
    fun getPlayers(room: DugoutRoom): List<Player> {
        return dugoutMap[room] ?: throw NoSuchElementException("Room $room not present in ${dugoutMap.keys}")
    }

    /**
     * The Player will be removed from the Dugout. The Player's Dugout property
     * wil be set to null.
     *
     * @param player
     * Player to remove
     * @return true if the player was in the Dugout and it was removed
     * successfully, false otherwise.
     */
    fun removePlayer(player: Player): Boolean {
        var res = false

        val rooms = dugoutMap.keys
        for (room in rooms) {
            val players = dugoutMap[room]
            if (players != null) {
                res = players.remove(player)
                if (res) {
                    player.isInDugout = false
                    setChanged()
                    break
                }
            }
        }
        notifyObservers()

        return res
    }

    /**
     * Search for the DugoutRoom where is the Player
     *
     * @param player
     * Player to search
     * @return The Player DugoutRoom
     */
    fun getRoom(player: Player): DugoutRoom? {
        var res: DugoutRoom? = null

        for (room in dugoutMap.keys) {
            val players = dugoutMap[room]
            if (players != null && players.contains(player)) {
                res = room
                break
            }
        }

        return res
    }

    /**
     * Add the Player to a specified Room in the Dugout. The Player's Dugout
     * property will be set to this Dugout. If the Room doesn't exist
     *
     * @param room
     * Room where to put the Player
     * @param player
     * Player to add to the Room
     * @return true if the player has been added to the Room
     */
    fun addPlayer(room: DugoutRoom, player: Player): Boolean {
        val res: Boolean

        // If the Player is already in this Dugout first it will be removed from
        // old Room
        if (player.isInDugout) {
            removePlayer(player)
        }

        val players = dugoutMap[room]
        if (players != null) {
            res = players.add(player)
            if (res) {
                player.isInDugout = true
                setChanged()
                notifyObservers()
            }
        } else {
            throw DugoutException("Room " + room
                    + " not present in Dugout!")
        }
        return res
    }

    fun getRoom(dugoutRoom: String): DugoutRoom =
            rooms.first { it.getRoomName().startsWith(dugoutRoom, true) }

}
