package bbarena.ds;

import bbarena.model.team.Roster;

/**
 * This interface allows to load Rosters from a persistence
 *
 * @author f.bellentani
 */
public interface RosterDS {

	public Roster getRoster(String name);

//	public List<String> getRosterNames();

//	public List<Roster> getRosters();

}
