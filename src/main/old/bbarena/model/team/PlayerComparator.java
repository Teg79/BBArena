package bbarena.model.team;

import java.util.Comparator;

/**
 * @author f.bellentani
 * 
 */
public class PlayerComparator implements Comparator<Player> {

	public enum PlayerComparatorType {
		COMPARE_NUM, COMPARE_SPP, COMPARE_CMP, COMPARE_CAS, COMPARE_INT, COMPARE_TD, COMPARE_MVP, COMPARE_SCR;
	}

	private PlayerComparatorType compareType = PlayerComparatorType.COMPARE_NUM;

	/** Creates a new instance of PlayerComparator */
	public PlayerComparator() {
		this(PlayerComparatorType.COMPARE_NUM);
	}

	public PlayerComparator(PlayerComparatorType compareType) {
		this.compareType = compareType;
	}

	public int compare(Player a, Player b) {
		int res = 0;

		if (a.getTeam() != null && b.getTeam() != null) {
			res = a.getTeam().getName().compareTo(b.getTeam().getName());
		}

		if (res == 0) {
			if (compareType == PlayerComparatorType.COMPARE_SPP) {
				res = compareSpp(a, b);
			} else if (compareType == PlayerComparatorType.COMPARE_CMP) {
				res = compareCmp(a, b);
			} else if (compareType == PlayerComparatorType.COMPARE_CAS) {
				res = compareCas(a, b);
			} else if (compareType == PlayerComparatorType.COMPARE_TD) {
				res = compareTd(a, b);
			} else if (compareType == PlayerComparatorType.COMPARE_INT) {
				res = compareInt(a, b);
			} else if (compareType == PlayerComparatorType.COMPARE_MVP) {
				res = compareMvp(a, b);
			} else if (compareType == PlayerComparatorType.COMPARE_SCR) {
				res = compareScr(a, b);
			} else {
				res = compareNum(a, b);
			}
		}

		if (res == 0 && compareType == PlayerComparatorType.COMPARE_NUM) {
			res = b.getName().compareTo(a.getName());
		}

		return res;
	}

	private int compareNum(Player a, Player b) {
		return a.getNum() - b.getNum();
	}

	private int compareSpp(Player a, Player b) {
		return b.getExperience().getSpp() - a.getExperience().getSpp();
	}

	private int compareCmp(Player a, Player b) {
		return b.getExperience().getComp() - a.getExperience().getComp();
	}

	private int compareCas(Player a, Player b) {
		return b.getExperience().getCas() - a.getExperience().getCas();
	}

	private int compareTd(Player a, Player b) {
		return b.getExperience().getTd() - a.getExperience().getTd();
	}

	private int compareInt(Player a, Player b) {
		return b.getExperience().getInter() - a.getExperience().getInter();
	}

	private int compareMvp(Player a, Player b) {
		return b.getExperience().getMvp() - a.getExperience().getMvp();
	}

	private int compareScr(Player a, Player b) {
		return b.getExperience().getScars() - a.getExperience().getScars();
	}

	/**
	 * Getter for property compareType.
	 * 
	 * @return Value of property compareType.
	 * 
	 */
	public PlayerComparatorType getCompareType() {
		return compareType;
	}

	/**
	 * Setter for property compareType.
	 * 
	 * @param compareType
	 *            New value of property compareType.
	 * 
	 */
	public void setCompareType(PlayerComparatorType compareType) {
		this.compareType = compareType;
	}

}
