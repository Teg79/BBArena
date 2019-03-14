package bbarena.model.event


/**
 *
 * @author cammerucci
 */
fun buildRollType(type: RollType.StandardRollType) = RollType(type.code, type.diceNumber, type.die)

data class RollType(val code: String, val diceNumber: Int, val die: Die) {

    enum class StandardRollType private constructor(val code: String, val diceNumber: Int, val die: Die) {
        GO_FOR_IT("GFI", 1, Die.D6),
        BLOCK_1("BK1", 1, Die.DB),
        BLOCK_2("BK2", 2, Die.DB),
        BLOCK_3("BK3", 3, Die.DB),
        BLITZ_1("BZ1", 1, Die.DB),
        BLITZ_2("BZ2", 2, Die.DB),
        BLITZ_3("BZ3", 3, Die.DB),
        DODGE("DOG", 1, Die.D6),
        PICK_UP("PIC", 1, Die.D6),
        PASS("PAS", 1, Die.D6),
        HAND_OFF("HOF", 1, Die.D6),
        INTERCEPTION("INT", 1, Die.D6),
        THROW_TEAM_MATE("TTM", 1, Die.D6),
        CATCH("CAT", 1, Die.D6),
        FOUL("FOU", 2, Die.D6),
        BONE_HEAD("BHD", 1, Die.D6),
        REALLY_STUPID("BHD", 1, Die.D6),
        THICK_SKULL("TSK", 1, Die.D6),
        REGENRATION("REG", 1, Die.D6),
        ARMOUR("ARM", 2, Die.D6),
        INJURY("INJ", 2, Die.D6),
        CASUALTY("CAS", 1, Die.D68),
        THROW_IN_DIRECTION("TID", 1, Die.D3),
        THROW_IN("TIN", 2, Die.D6),
        SCATTER("SCA", 1, Die.DS),
        WEATHER("WEA", 2, Die.D6),
        KICK_OFF("KOF", 2, Die.D6),
        APOTECARY("APO", 1, Die.D6)
    }

}
