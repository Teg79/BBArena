package net.sf.bbarena.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.sf.bbarena.model.pitch.Pitch;
import net.sf.bbarena.model.pitch.PitchFactory;
import net.sf.bbarena.model.team.Team;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the starting class of the model. The Pitch will be created here and
 * here the teams will added to the game. To modify the model in a reversible
 * way put Events in the EventLine and go forward and backward.
 *
 * @author f.bellentani
 */
public class Arena implements Serializable {

	public enum MatchStatus {
		STARTING, PLAYING, ENDING, FINISHED;
	}

	private static final long serialVersionUID = 7731121291373282840L;

	private MatchStatus _status = null;

	private Date _start = null;

	private Date _end = null;

	private static final Logger _log = LoggerFactory.getLogger("arena");

	private static final Map<String, Arena> _arenaMap = new HashMap<String, Arena>();

	private String _matchId = null;

	private Pitch _pitch = null;

	private List<Team> _teams = new ArrayList<Team>();

	private PlayerManager _playerManager = null;

	private TurnMarker _turnMarker = null;

	private Score _score = null;

	private Referee _referee = null;

	private Weather _weather = Weather.getWeather(Weather.WeatherType.NICE);

	/**
	 * This constructor must be used to create a new Match with the Default
	 * Pitch Type
	 *
	 * @param teams
	 *            List of the Teams that will play in the Match
	 */
	public Arena(Team... teams) {
		this(null, teams);
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
	public Arena(String pitchType, Team... teams) {
		this(pitchType, null, null, null, teams);
	}

	/**
	 * This constructor must be used only by the persistence to load a Match
	 *
	 * @param pitchType
	 *            Pitch used
	 * @param teams
	 *            List of the Teams that play in the Match
	 * @param status
	 *            Status of the Match
	 * @param start
	 *            Date of the start of the Match
	 * @param end
	 *            Date of the end of the Match (can be null)
	 */
	public Arena(String pitchType,
			MatchStatus status, Date start, Date end, Team... teams) {
		_matchId = UUID.randomUUID().toString();
		_teams = Arrays.asList(teams);

		if (pitchType == null || pitchType.trim().length() == 0) {
			_log.debug("Creating default pitch...");
			_pitch = PitchFactory.getPitch(teams);
		} else {
			_log.debug("Creating " + pitchType + " pitch...");
			_pitch = PitchFactory.getPitch(pitchType, teams);
		}
		_log.debug("Pitch created!");

		if (status == null) {
			_status = MatchStatus.STARTING;
			_start = new Date();
			_end = null;
		} else {
			_status = status;
			_start = start;
			_end = end;
		}

		_playerManager = new PlayerManager(this);
		_turnMarker = new TurnMarker(teams);

		_score = new Score(teams);
		_referee = new Referee(teams);

		_arenaMap.put(_matchId, this);
	}

	public static Arena getArena(Long matchId) {
		return _arenaMap.get(matchId);
	}

	public static Set<String> getArenaIds() {
		return _arenaMap.keySet();
	}

	public Pitch getPitch() {
		return _pitch;
	}

	public List<Team> getTeams() {
		return _teams;
	}

	public TurnMarker getTurnMarker() {
		return _turnMarker;
	}

	public Score getScore() {
		return _score;
	}

	public Referee getReferee() {
		return _referee;
	}

	public PlayerManager getPlayerManager() {
		return _playerManager;
	}

	public String getMatchId() {
		return _matchId;
	}

	public MatchStatus getStatus() {
		return _status;
	}

	public void setStatus(MatchStatus status) {
		_status = status;
		if (_status == MatchStatus.FINISHED) {
			_end = new Date();
		}
	}

	public Date getEnd() {
		return _end;
	}

	public Date getStart() {
		return _start;
	}

	public MatchInfo getMatchInfo() {
		MatchInfo res = new MatchInfo();

		res.setEnd(_end);
		res.setMatchId(_matchId);
		res.setStart(_start);
		res.setStatus(_status);
		res.setScore(_score);

		return res;
	}

	public Weather getWeather() {
		return _weather;
	}

	public void setWeather(Weather weather) {
		_weather = weather;
	}
}
