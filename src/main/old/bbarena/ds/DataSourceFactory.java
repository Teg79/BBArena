package bbarena.ds;

import bbarena.ds.util.xml.XmlRosterDS;

//GABRIELE
//BELLENTANI

public class DataSourceFactory {

	public RosterDS getRosterDS(String rules) {
		return new XmlRosterDS(rules, true);
	}

	public RosterDS getRosterDS(String rules, boolean isLocal){
		return new XmlRosterDS(rules, isLocal);
	}

	public TeamDS getTeamDS() {
		//TODO: To implement with the creation of a TeamDS
		return null;
	}

	public MatchDS getMatchDS() {
		//TODO: To implement with the creation of a MatchDS
		return null;
	}

}
