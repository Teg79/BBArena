package bbarena.model.event

import bbarena.model.Arena
import bbarena.model.RollResult
import bbarena.model.util.Concat
import java.io.Serializable
import java.util.*

abstract class Event : Serializable {

    var id = 0
    private val _diceRolls = ArrayList<RollResult>()
    var isExecuted = false

    val diceRolls: List<RollResult>
        get() = _diceRolls

    open fun getString() = Concat.buildLog(javaClass)

    fun addDiceRoll(diceRoll: RollResult) {
        _diceRolls.add(diceRoll)
    }

    abstract fun doEvent(arena: Arena)

    abstract fun undoEvent(arena: Arena)

    override fun toString(): String {
        val res = StringBuilder("[").append(id).append("] ")
        res.append(getString()).append(" ").append(_diceRolls.toString())
        return res.toString()
    }

        /*
		MoveBall
		AddBlockTarget
		ClearBlockTargets
		SetBomb (sets bomb on field square)
		AddPlayerToField
		SetHalf
		AddLogRow
		AddPath (moved path shown as numbers, adds one number to current path)
		ClearPath (clears whole path)
		SetActivePlayer
		SetBallCarrier
		SetBallInGame (sets ball's inGame value to true, i.e. ball will be shown)
		SetBlocker (sets blocker)
		SetBlockResult (sets block result)
		SetKickoffResult (sets kickoff result)
		SetPlayerAction (sets current players action i.e. move, block, blitz, etc.)
		SpellSet (sets active spell)
		SetMatchState (sets matches global state, if I only could remember what those were)
		ChangeWeather (sets current weather)
		PlayerMove
		PlayerSetInjuryText
		PlayerSetStatus
		TeamApothecarySet
		MovePlayerToDugout
		TeamSetRerolls
		TeamSetScore
		TeamSetSpecials (get the ref and who the referee is watching at)
		TeamSetTurn
		TeamWizardSet
	*/
}
