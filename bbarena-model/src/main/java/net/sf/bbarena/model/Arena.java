package net.sf.bbarena.model;

import net.sf.bbarena.model.pitch.Pitch;
import net.sf.bbarena.model.pitch.PitchFactory;
import net.sf.bbarena.model.team.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the starting class of the model. The Pitch will be created here and
 * here the teams will added to the game. To modify the model in a reversible
 * way put Events in the EventLine and go forward and backward.
 *
 * @author f.bellentani
 */
public class Arena implements Serializable {

	private static final long serialVersionUID = 7731121291373282840L;

	private static final Logger _log = LoggerFactory.getLogger("arena");

	private Match _match = null;

	private Pitch _pitch = null;

	private List<Team> _teams = new ArrayList<>();

	private PlayerManager _playerManager = null;

	private List<TurnMarker> _teamMarkers = new ArrayList<>();

    private List<ScoreBoard> _scoreBoards = new ArrayList<>();
    
	private Referee _referee = null;

	private Weather _weather = Weather.getWeather(Weather.WeatherType.NICE);

    private int _time = 0;

	/**
	 * This constructor must be used to create a new Match with the Default
	 * Pitch Type
	 *
	 * @param teams
	 *            List of the Teams that will play in the Match
	 */
	public Arena(Match match, Team... teams) {
		this(match, null, teams);
	}

	/**
	 * This constructor must be used to create a new Match with a given Pitch
	 * Type
	 *
	 * @param pitchType
	 *            Pitch used
	 * @param teams
	 *            List of the Teams that will play in the Match
	 */
	public Arena(Match match, String pitchType, Team... teams) {
		_match = match;
		_teams = Arrays.asList(teams);

		if (pitchType == null || pitchType.trim().length() == 0) {
			_log.debug("Creating default pitch...");
			_pitch = PitchFactory.getPitch(teams);
		} else {
			_log.debug("Creating " + pitchType + " pitch...");
			_pitch = PitchFactory.getPitch(pitchType, teams);
		}
		_log.debug("Pitch created!");

		_playerManager = new PlayerManager(this);
		for (Team team : teams) {
			_teamMarkers.add(new TurnMarker(team));
			_scoreBoards.add(new ScoreBoard());
		}		

		_referee = new Referee(teams);
	}

	public Match getMatch() {
		return _match;
	}

	public Pitch getPitch() {
		return _pitch;
	}

	public List<Team> getTeams() {
		return _teams;
	}

    public ScoreBoard getScoreBoard(int team) {
        return _scoreBoards.get(team);
    }

	public Referee getReferee() {
		return _referee;
	}

	public PlayerManager getPlayerManager() {
		return _playerManager;
	}

	public Weather getWeather() {
		return _weather;
	}

	public void setWeather(Weather weather) {
		_weather = weather;
	}

	public List<TurnMarker> getTurnMarkers() {
		return _teamMarkers;
	}

	public List<ScoreBoard> getScoreBoards() {
		return _scoreBoards;
	}

    public int getHalf() {
        return _time;
    }

    public void setHalf(int time) {
        _time = time;
    }
}
