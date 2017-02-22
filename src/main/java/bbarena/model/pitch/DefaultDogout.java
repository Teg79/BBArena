package bbarena.model.pitch;

import bbarena.model.team.Team;

/**
 * BloodBowlDugout is the Dugout implementation for the classic BB Pitch
 * 
 * @author f.bellentani
 */
public class DefaultDogout extends Dugout {

	private static final long serialVersionUID = -1130399654603614931L;

	public enum BloodBowlDugoutRoom implements DugoutRoom {
		RESERVES, KO, BH, SI, RIP;
	}

	public DefaultDogout(Team team) {
		super(team, BloodBowlDugoutRoom.values(), BloodBowlDugoutRoom.RESERVES);
	}

	@Override
	public DugoutRoom getRoom(String dugoutRoom) {
		DugoutRoom res = null;

		for (DugoutRoom room : BloodBowlDugoutRoom.values()) {
			if (room.toString().startsWith(dugoutRoom.toUpperCase())) {
				res = room;
				break;
			}
		}

		return res;
	}

}
