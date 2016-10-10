package net.sf.bbarena.ds;

import java.util.List;

import net.sf.bbarena.model.team.Team;
import net.sf.bbarena.model.team.TeamInfo;

public interface TeamDS {

	public Team getTeam(long teamId);
	
	public List<TeamInfo> getTeams(String coach);
	
	public void addTeam(Team team);
	
	public void saveTeam(Team team);
	
}
