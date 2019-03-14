package bbarena.model.rules.sb

import bbarena.model.rules.bb.PitchFactory
import bbarena.model.team.Team

val PITCH_NAME = "Street Bowl"
val WIDTH = 26
val HEIGHT = 7
val WIDE_ZONE = 2

fun buildPitch(teams: List<Team>) = PitchFactory(PITCH_NAME, WIDTH, HEIGHT, WIDE_ZONE, teams)
