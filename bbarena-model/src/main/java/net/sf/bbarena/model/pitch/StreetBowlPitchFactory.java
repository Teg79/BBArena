package net.sf.bbarena.model.pitch;

import net.sf.bbarena.model.team.Team;

/**
 * Factory to create the Street Bowl Pitch
 *
 * @author f.bellentani
 */
public class StreetBowlPitchFactory extends PitchFactory {

	public static final String PITCH_NAME = "Street Bowl";
	public static final int WIDTH = 26;
	public static final int HEIGHT = 7;
	public static final int WIDE_ZONE = 2;

	public StreetBowlPitchFactory(Team... teams) {
		super(PITCH_NAME, WIDTH, HEIGHT, WIDE_ZONE, teams);
	}
}
