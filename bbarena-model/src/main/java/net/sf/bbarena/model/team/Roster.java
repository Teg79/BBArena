package net.sf.bbarena.model.team;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author f.bellentani
 */
public class Roster implements Serializable {
	
	private static final long serialVersionUID = -6997515918058052110L;

	private String race;
	private String description;
	private List<PlayerTemplate> players;
	private Qty rerolls;
	private long rerollCost;
	private List<String> starsId;
	
	public Roster(String race, String description, int minRerolls, int maxRerolls, long rerollCost) {
		this.race = race;
		this.description = description;
		this.rerolls = new Qty(minRerolls, maxRerolls);
		this.rerollCost = rerollCost;
		
		this.players = new ArrayList<PlayerTemplate>();
		this.starsId = new ArrayList<String>();
	}
	
	public boolean addPlayer(PlayerTemplate player) {
		boolean res;
		player.setRoster(this);
		res = this.players.add(player);
		
		return res;
	}
	
	public boolean addStarId(String starId) {
		boolean res;
		res = starsId.add(starId);
		
		return res;
	}

	public String getDescription() {
		return description;
	}

	public List<PlayerTemplate> getPlayers() {
		return players;
	}

	public String getRace() {
		return race;
	}

	public long getRerollCost() {
		return rerollCost;
	}

	public Qty getRerolls() {
		return rerolls;
	}

	public List<String> getStarsId() {
		return starsId;
	}

}
