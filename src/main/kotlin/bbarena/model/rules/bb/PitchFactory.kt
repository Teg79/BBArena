package bbarena.model.rules.bb

import bbarena.model.Coordinate
import bbarena.model.pitch.Ball
import bbarena.model.pitch.Pitch
import bbarena.model.pitch.Square.SquareType
import bbarena.model.team.Team
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

val DEFAULT_PITCH_NAME = "BB"
val DEFAULT_WIDTH = 26
val DEFAULT_HEIGHT = 15
val DEFAULT_WIDE_ZONE = 4

fun buildPitch(teams: List<Team>) = PitchFactory(DEFAULT_PITCH_NAME, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_WIDE_ZONE, teams).buildPitch()

class PitchFactory constructor(private val name: String, private val width: Int, private val height: Int, private val wideZone: Int, val teams: List<Team>) {

    fun getTeam(team: Int) = teams[team]

    fun buildPitch(): Pitch {
        val res = Pitch(name, width, height)
        for (x in 0 until width) {
            for (y in 0 until height) {
                val xy = Coordinate(x, y)
                var type = SquareType.NORMAL

                if (x == 0 || x == width - 1) {
                    type = SquareType.END_ZONE
                } else if (y < wideZone) {
                    type = SquareType.RIGHT_WIDE_ZONE
                } else if (y >= height - wideZone) {
                    type = SquareType.LEFT_WIDE_ZONE
                } else if (x == width / 2 || x == width / 2 - 1) {
                    type = SquareType.LOS
                }

                res.setSquare(xy, type, getTeamOwner(x, y))
            }
        }
        res.addDugout(buildBBDugout(getTeam(0)))
        res.addDugout(buildBBDugout(getTeam(1)))

        res.addBall(Ball())

        return res
    }

    private fun getTeamOwner(x: Int, y: Int): Team? {
        var res: Team? = null
        if (y in 0..(height - 1) && x >= 0 && x < width) {
            res = if (x < width / 2) {
                getTeam(0)
            } else {
                getTeam(1)
            }
        }
        return res
    }

}
