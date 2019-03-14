package bbarena.model

import bbarena.model.pitch.Pitch
import bbarena.model.team.Team
import mu.KotlinLogging
import java.io.Serializable
import java.util.*

/**
 * This is the starting class of the model. The Pitch will be created here and
 * here the teams will added to the game. To modify the model in a reversible
 * way put Events in the EventLine and go forward and backward.
 *
 */

private val logger = KotlinLogging.logger {}

class Arena (val match: Match<*>, val teams: List<Team>, pitchFactory: () -> Pitch) : Serializable {

    val pitch = pitchFactory.invoke()

    val playerManager: PlayerManager

    val turnMarkers = ArrayList<TurnMarker>()

    val scoreBoards = ArrayList<ScoreBoard>()

    val referee: Referee

    var weather = getWeather(WeatherType.NICE)

    var half = 0

    init {
        logger.debug("Pitch created!")

        playerManager = PlayerManager(this)
        for (team in teams) {
            turnMarkers.add(TurnMarker(team))
            scoreBoards.add(ScoreBoard())
        }

        referee = Referee(teams)
    }

    fun getScoreBoard(team: Int): ScoreBoard {
        return scoreBoards[team]
    }

}
