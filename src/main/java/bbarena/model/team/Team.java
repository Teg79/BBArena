package bbarena.model.team;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class Team implements Serializable {

	private static final long serialVersionUID = 3467542477164289313L;

	private long id = 0;

	private Set<Player> players = new TreeSet<Player>(new PlayerComparator());

	private String name = "";

	private Roster roster = null;

	private TeamCoach coach = new TeamCoach();

	private long treasury = 0;

	private long pettyCash = 0;

	private int reRolls = 0;

	private int fanFactor = 0;

	private int assistants = 0;

	private int cheerleaders = 0;

	private boolean apothecary = false;

	private boolean apoUsed = false;

	private int wizard = 0;

	public Team(long id, Roster roster) {
		this.id = id;
		this.roster = roster;
	}

	public boolean equals(Object o) {
		return this == o;
	}

	public int getTeamRating() {
		int res = (int) Math.floor((getTotalCost() + getTreasury()) / 10000);
		res += Math.floor(getTotalSpp() / 5);
		return res;
	}

	/**
	 * Calculates the Team Value of the Team
	 *
	 * @return Team Value
	 */
	public long getTeamValue() {
		long tv = getTeamCost();
		tv += getPettyCash();
		for (Player p : players) {
			tv += p.getValue();
		}

		return tv;
	}

	public int getTotalSpp() {
		int res = 0;
		for (Player p : players) {
			res += p.getExperience().getSpp() + p.getExperience().getOld();
		}
		return res;
	}

	/**
	 * Calculates the sum of all players, rerolls, FF, AC, CL and Apo. No
	 * Treasury!
	 *
	 * @return Total Cost of the Team
	 */
	public long getTotalCost() {
		long res = 0;
		for (Player p : players) {
			res += p.getCost();
		}
		res += getTeamCost();
		return res;
	}

	private long getTeamCost() {
		long res = 0;
		res += getReRolls() * getReRollCost();
		res += getFanFactor() * 10000;
		res += getAssistants() * 10000;
		res += getCheerleaders() * 10000;
		if (isApothecary()) {
			res += 50000;
		}
		return res;
	}

	/**
	 * Overwrite the player with the same number o add the player if his
	 * position is free
	 *
	 * @param player
	 *            Player to set
	 * @return The old player or null if the position was free
	 */
	public Player setPlayer(Player player) {
		boolean done = false;
		Player res = null;

		for (Player p : players) {
			if (p.getNum() == player.getNum()) {
				player.setTeam(this);
				players.remove(p);
				res = p;
				players.add(player);
				done = true;
				break;
			}
		}
		if (!done) {
			addPlayer(player);
			res = null;
		}

		return res;
	}

	public void addPlayer(Player player) {
		player.setTeam(this);
		players.add(player);
	}

	public Player removePlayer(int num) {
		Player res = null;

		for (Player p : players) {
			if (p.getNum() == num) {
				res = p;
				players.remove(p);
				break;
			}
		}
		return res;
	}

	public Player getPlayer(int num) {
		for (Player p : players) {
			if (p.getNum() == num) {
				return p;
			}
		}

		return null;
	}

	/**
	 * Getter for property players.
	 *
	 * @return Value of property players.
	 *
	 */
	public Set<Player> getPlayers() {
		return players;
	}

	/**
	 * Setter for property players.
	 *
	 * @param players
	 *            New value of property players.
	 *
	 */
	public void setPlayers(Set<Player> players) {
		this.players = players;
	}

	/**
	 * Getter for property name.
	 *
	 * @return Value of property name.
	 *
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for property name.
	 *
	 * @param name
	 *            New value of property name.
	 *
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for property race.
	 *
	 * @return Value of property race.
	 *
	 */
	public String getRace() {
		return roster.getRace();
	}

	/**
	 * Getter for property coach.
	 *
	 * @return Value of property coach.
	 *
	 */
	public TeamCoach getCoach() {
		return coach;
	}

	/**
	 * Setter for property coach.
	 *
	 * @param coach
	 *            New value of property coach.
	 *
	 */
	public void setCoach(TeamCoach coach) {
		this.coach = coach;
	}

	/**
	 * Getter for property treasury.
	 *
	 * @return Value of property treasury.
	 *
	 */
	public long getTreasury() {
		return treasury;
	}

	/**
	 * Setter for property treasury.
	 *
	 * @param treasury
	 *            New value of property treasury.
	 *
	 */
	public void setTreasury(int treasury) {
		this.treasury = treasury;
	}

	/**
	 * Getter for property reRolls.
	 *
	 * @return Value of property reRolls.
	 *
	 */
	public int getReRolls() {
		return reRolls;
	}

	/**
	 * Setter for property reRolls.
	 *
	 * @param reRolls
	 *            New value of property reRolls.
	 *
	 */
	public void setReRolls(int reRolls) {
		this.reRolls = reRolls;
	}

	/**
	 * Getter for property reRollsCost.
	 *
	 * @return Value of property reRollsCost.
	 *
	 */
	public long getReRollCost() {
		long res = 0;

		if(this.roster != null) {
			res = this.roster.getRerollCost();
		}

		return res;
	}

	/**
	 * Getter for property fanFactor.
	 *
	 * @return Value of property fanFactor.
	 *
	 */
	public int getFanFactor() {
		return fanFactor;
	}

	/**
	 * Setter for property fanFactor.
	 *
	 * @param fanFactor
	 *            New value of property fanFactor.
	 *
	 */
	public void setFanFactor(int fanFactor) {
		this.fanFactor = fanFactor;
	}

	/**
	 * Getter for property assistants.
	 *
	 * @return Value of property assistants.
	 *
	 */
	public int getAssistants() {
		return assistants;
	}

	/**
	 * Setter for property assistants.
	 *
	 * @param assistants
	 *            New value of property assistants.
	 *
	 */
	public void setAssistants(int assistants) {
		this.assistants = assistants;
	}

	/**
	 * Getter for property cheerleaders.
	 *
	 * @return Value of property cheerleaders.
	 *
	 */
	public int getCheerleaders() {
		return cheerleaders;
	}

	/**
	 * Setter for property cheerleaders.
	 *
	 * @param cheerleaders
	 *            New value of property cheerleaders.
	 *
	 */
	public void setCheerleaders(int cheerleaders) {
		this.cheerleaders = cheerleaders;
	}

	/**
	 * Getter for property apothecary.
	 *
	 * @return Value of property apothecary.
	 *
	 */
	public boolean isApothecary() {
		return apothecary;
	}

	/**
	 * Setter for property apothecary.
	 *
	 * @param apothecary
	 *            New value of property apothecary.
	 *
	 */
	public void setApothecary(boolean apothecary) {
		this.apothecary = apothecary;
	}

	/**
	 * Getter for property wizard.
	 *
	 * @return Value of property wizard.
	 *
	 */
	public int getWizard() {
		return wizard;
	}

	/**
	 * Setter for property wizard.
	 *
	 * @param wizard
	 *            New value of property wizard.
	 *
	 */
	public void setWizard(int wizard) {
		this.wizard = wizard;
	}

	public long getPettyCash() {
		return pettyCash;
	}

	public void transferGoldToPettyCash(long gold) {
		this.treasury -= gold;
		this.pettyCash += gold;
	}

	public boolean isApoUsed() {
		return apoUsed;
	}

	public void setApoUsed(boolean apoUsed) {
		this.apoUsed = apoUsed;
	}

	public Roster getRoster() {
		return roster;
	}

	public long getId() {
		return id;
	}

	public TeamInfo getTeamInfo() {
		String coachName = (this.coach != null ? this.coach.getName() : "");
		String race = (this.roster != null ? this.roster.getRace() : "");
		TeamInfo res = new TeamInfo(this.id, this.name, coachName, race, this.getTeamValue());
		return res;
	}

}
