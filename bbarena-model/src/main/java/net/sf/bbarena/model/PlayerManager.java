package net.sf.bbarena.model;

import java.io.Serializable;
import java.util.List;

import net.sf.bbarena.model.team.Experience;
import net.sf.bbarena.model.team.Player;
import net.sf.bbarena.model.team.Team;

public class PlayerManager implements Serializable {

	private static final long serialVersionUID = -8487333002274618063L;

	private Arena arena = null;

	private Player selectedPlayer = null;

	protected PlayerManager(Arena arena) {
		this.arena = arena;
	}

	public void setSelectedPlayer(Player player) {
		this.selectedPlayer = player;
	}

	public Player getSelectedPlayer() {
		return selectedPlayer;
	}

	public void addPlayerExperience(Player player, Experience exp) {
		Experience e = player.getNewExperience();
		e.add(exp);
	}

	public void subPlayerExperience(Player player, Experience exp) {
		Experience e = player.getNewExperience();
		e.sub(exp);
	}

	public Player getPlayer(long playerId) {
		Player res = null;
		List<Team> teams = arena.getTeams();

		for (Team team : teams) {
			for (Player player : team.getPlayers()) {
				if (player.getId() == playerId) {
					res = player;
					break;
				}
			}
			if (res != null) {
				break;
			}
		}

		return res;
	}

}
