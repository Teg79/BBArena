package bbarena.model.team

import bbarena.model.exception.PitchException

fun getPitchStatus(pitchStatus: Int) =
    if (pitchStatus in 0..2) PlayerPitchStatus.values()[pitchStatus]
    else throw PitchException("Pitch status $pitchStatus is not supported")

enum class PlayerPitchStatus {
    STANDING, PRONE, STUNNED
}
