package net.sf.bbarena.model.pitch;

import java.util.Arrays;
import java.util.List;

import net.sf.bbarena.model.Coordinate;
import net.sf.bbarena.model.pitch.Square.SquareType;
import net.sf.bbarena.model.team.Team;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author f.bellentani
 *
 */
public class PitchFactory {
	
	private static final Logger log = LoggerFactory.getLogger(PitchFactory.class);

	public static final String DEFAULT_PITCH_TYPE = "DEFAULT";
	public static final String DEFAULT_PITCH_NAME = "BB";
	public static final int DEFAULT_WIDTH = 26;
	public static final int DEFAULT_HEIGHT = 15;
	public static final int DEFAULT_WIDE_ZONE = 4;
	
	private List<Team> _teams = null;
	private String _name;
	private int _width;
	private int _height;
	private int _wideZone;
	
	protected PitchFactory(Team... teams) {
		this(DEFAULT_PITCH_NAME, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_WIDE_ZONE, teams);
	}
	
	protected PitchFactory(String name, Team... teams) {
		this(name, 0, 0, 0, teams);
	}

	protected PitchFactory(String name, int width, int height, int wideZone, Team... teams) {
		_name = name;
		_width = width;
		_height = height;
		_wideZone = wideZone;
		_teams = Arrays.asList(teams);
	}

	public Team getTeam(int team) {
		return _teams.get(team);
	}

	public List<Team> getTeams() {
		return _teams;
	}	
	
	protected Pitch buildPitch() {
		Pitch res = new Pitch(_name, _width, _height);
		for(int x = 0; x < _width; x++) {
			for(int y = 0; y < _height; y++) {
				Coordinate xy = new Coordinate(x, y);
				SquareType type = SquareType.NORMAL;

				if(x == 0 || x == _width-1) {
					type = SquareType.END_ZONE;
				} else if(y < _wideZone || y >= _height-_wideZone) {
					type = SquareType.WIDE_ZONE;
				} else if(x == _width/2 || x == (_width/2)-1) {
					type = SquareType.LOS;
				}

				res.setSquare(xy, type, getTeamOwner(x, y));
			}
		}
		res.addDugout(new DefaultDogout(getTeam(0)));
		res.addDugout(new DefaultDogout(getTeam(1)));

		res.addBall(new Ball());

		return res;
	}

	private Team getTeamOwner(int x, int y) {
		Team res = null;
		if(y >= 0 && y < _height && x >= 0 && x < _width) {
			if(x < _width/2) {
				res = getTeam(0);
			} else {
				res = getTeam(1);
			}
		}
		return res;
	}

	public static Pitch getPitch(Team... teams) {
		// TODO: Load params the pitchType
		String pitchType = DEFAULT_PITCH_TYPE;
		return getPitch(pitchType, teams);
	}

	public static Pitch getPitch(String pitchType, Team... teams) {
		// TODO: Load from properties the PitchFactory implementation
		PitchFactory pf = new PitchFactory(teams);
		return pf.buildPitch();
	}

}
