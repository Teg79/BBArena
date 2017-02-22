package bbarena.model.pitch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import bbarena.model.exception.DugoutException;
import bbarena.model.team.Player;
import bbarena.model.team.Team;

/**
 * This class is the base implementation for a Dugout and must be extended for
 * each Pitch implementation
 * 
 * @author f.bellentani
 */
public abstract class Dugout extends Observable implements Serializable {

	private static final long serialVersionUID = 5966308207867121784L;

	/**
	 * This interface must be implemented with an Enum to represent the possible
	 * Dogout Rooms for a Pitch implementation.
	 * 
	 * @author f.bellentani
	 */
	public interface DugoutRoom extends Serializable {
	}

	private Team team = null;

	private Map<DugoutRoom, List<Player>> dugoutMap = null;

	public Dugout(Team team, DugoutRoom[] rooms, DugoutRoom startRoom) {
		super();
		
		this.team = team;

		this.dugoutMap = new HashMap<DugoutRoom, List<Player>>(rooms.length);

		for (DugoutRoom room : rooms) {
			this.dugoutMap.put(room, new ArrayList<Player>());
		}

		if (startRoom != null && team != null) {
			Set<Player> playersTeam = team.getPlayers();
			for (Player player : playersTeam) {
				addPlayer(startRoom, player);
			}
		}
	}

	/**
	 * Getter for the Team owner of this Dugout
	 * 
	 * @return The team owner of this Dugout
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Return the list of Players in a Room, null if the Room doesn't exist
	 * 
	 * @param room
	 *            The DugoutRoom
	 * @return The List of the Player in the Room, null if the Room doesn't
	 *         exist
	 */
	public List<Player> getPlayers(DugoutRoom room) {
		return dugoutMap.get(room);
	}

	/**
	 * The Player will be removed from the Dugout. The Player's Dugout property
	 * wil be set to null.
	 * 
	 * @param player
	 *            Player to remove
	 * @return true if the player was in the Dugout and it was removed
	 *         successfully, false otherwise.
	 */
	protected boolean removePlayer(Player player) {
		boolean res = false;

		Set<DugoutRoom> rooms = dugoutMap.keySet();
		for (DugoutRoom room : rooms) {
			List<Player> players = dugoutMap.get(room);
			if (players != null) {
				res = players.remove(player);
				if (res) {
					player.setInDugout(false);
					setChanged();
					break;
				}
			}
		}
		notifyObservers();

		return res;
	}

	/**
	 * Search for the DugoutRoom where is the Player
	 * 
	 * @param player
	 *            Player to search
	 * @return The Player DugoutRoom
	 */
	public DugoutRoom getRoom(Player player) {
		DugoutRoom res = null;

		for (DugoutRoom room : dugoutMap.keySet()) {
			List<Player> players = dugoutMap.get(room);
			if (players != null && players.contains(player)) {
				res = room;
				break;
			}
		}

		return res;
	}

	/**
	 * Add the Player to a specified Room in the Dugout. The Player's Dugout
	 * property will be set to this Dugout. If the Room doesn't exist
	 * 
	 * @param room
	 *            Room where to put the Player
	 * @param player
	 *            Player to add to the Room
	 * @return true if the player has been added to the Room
	 */
	protected boolean addPlayer(DugoutRoom room, Player player) {
		boolean res = false;

		// If the Player is already in this Dugout first it will be removed from
		// old Room
		if (player.isInDugout()) {
			removePlayer(player);
		}

		List<Player> players = dugoutMap.get(room);
		if (players != null) {
			res = players.add(player);
			if (res) {
				player.setInDugout(true);
				setChanged();
				notifyObservers();
			}
		} else {
			throw new DugoutException("Room " + room
					+ " not present in Dugout!");
		}
		return res;
	}

	public abstract DugoutRoom getRoom(String dugoutRoom);

}
