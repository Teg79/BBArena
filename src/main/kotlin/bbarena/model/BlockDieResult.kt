package bbarena.model

/**
 * Converts from a int (from 1 to 6) to a Block Die Result
 *
 * @param result Result from 1 to 6
 * @return Block Die Result
 */
fun getBlockDieResult(result: Int): BlockDieResult? {
    var res: BlockDieResult? = null

    // Block dice don't follow the common rule for dice faces (the sum of opposite faces is not 7)
    if (result == 1) {
        res = BlockDieResult.DEFENDER_STUMBLES
    } else if (result == 2) {
        res = BlockDieResult.ATTACKER_DOWN
    } else if (result == 3 || result == 4) {
        res = BlockDieResult.PUSHED
    } else if (result == 5) {
        res = BlockDieResult.BOTH_DOWN
    } else if (result == 6) {
        res = BlockDieResult.DEFENDER_DOWN
    } else {
        res = null
    }

    return res
}

enum class BlockDieResult {
    ATTACKER_DOWN,
    BOTH_DOWN,
    PUSHED,
    DEFENDER_STUMBLES,
    DEFENDER_DOWN;

}
