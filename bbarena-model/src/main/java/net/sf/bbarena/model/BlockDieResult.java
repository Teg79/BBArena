package net.sf.bbarena.model;


public enum BlockDieResult {
	ATTACKER_DOWN,
	BOTH_DOWN,
	PUSHED,
	DEFENDER_STUMBLES,
	DEFENDER_DOWN;

	/**
	 * Converts from a int (from 1 to 6) to a Block Die Result
	 *
	 * @param result Result from 1 to 6
	 * @return Block Die Result
	 */
	public static BlockDieResult getBlockDieResult(int result) {
		BlockDieResult res = null;
		
		// Block dice don't follow the common rule for dice faces (the sum of opposite faces is not 7)
		if(result == 1) {
			res = DEFENDER_STUMBLES;
		} else if(result == 2) {
			res = ATTACKER_DOWN;
		} else if(result == 3 || result == 4) {
			res = PUSHED;
		} else if(result == 5) {
			res = BOTH_DOWN;
		} else if(result == 6) {
			res = DEFENDER_DOWN;
		} else {
			res = null;
		}
		
		return res;
	}

}
