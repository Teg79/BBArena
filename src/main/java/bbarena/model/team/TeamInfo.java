package bbarena.model.team;

public class TeamInfo {

	private long id;
	private String name;
	private String coach;
	private String race;
	private long teamValue;

	public TeamInfo(long id, String name, String coach, String race, long teamValue) {
		this.id = id;
		this.name = name;
		this.coach = coach;
		this.race = race;
		this.teamValue = teamValue;
	}

	public String getCoach() {
		return coach;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRace() {
		return race;
	}

	public long getTeamValue() {
		return teamValue;
	}

	@Override
	public boolean equals(Object o) {
		boolean res = false;

		if(o instanceof TeamInfo) {
			TeamInfo ti = (TeamInfo)o;
			res = (this.id == ti.id);
		}

		return res;
	}

}
