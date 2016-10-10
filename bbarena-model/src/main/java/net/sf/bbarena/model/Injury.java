/**
 * 
 */
package net.sf.bbarena.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author teg
 * 
 */
public class Injury implements Serializable {

	private static final long serialVersionUID = 5575934750045433839L;

	public enum InjuryType {
		BH, MNG, N, SI, DEATH, AC, RETIRED;
		
		public static String combine(InjuryType... types) {
			StringBuffer res = new StringBuffer();
			for (InjuryType it : types) {
				if (res.length() > 0) {
					res.append(Injury.INJ_TYPE_SEPARATOR);
				}
				res.append(it);
			}
			return res.toString();
		}
	};

	public enum InjuryCause {
		Block, Movement, Foul, Crowd, Handicap, Event, Ageing, Wizard, Potion, Other
	};

	public enum InjuryCure {
		Healed, Regenerated, Potion, None;
		public String toString() {
			String res = null;
			if (this == Potion) {
				res = "Healed with Potion";
			} else {
				res = super.toString();
			}
			return res;
		}

		public static boolean isHealed(String cure) {
			boolean res = false;
			for (InjuryCure c : values()) {
				if (c.name().equalsIgnoreCase(cure)) {
					res = true;
					break;
				}
			}
			return res;
		}
	};

	public static final String INJ_TYPE_SEPARATOR = "+";

	public static final String INJ_TOKEN_SEPARATOR = " ";

	public static final String INJ_SEPARATOR = ";";

	private List<InjuryType> type = new ArrayList<InjuryType>(5);

	private InjuryCause cause = InjuryCause.Block;

	private InjuryCure cure = InjuryCure.None;

	private boolean healed = false;

	/** Creates a new instance of Injury */
	public Injury(String inj) {
		String it = inj.trim();

		StringTokenizer tok = new StringTokenizer(it, INJ_TOKEN_SEPARATOR);

		// Types
		String types = tok.nextToken();
		StringTokenizer typeTok = new StringTokenizer(types, INJ_TYPE_SEPARATOR);
		while (typeTok.hasMoreTokens()) {
			type.add(InjuryType.valueOf(typeTok.nextToken().toUpperCase()));
		}

		// Causes
		if (tok.hasMoreTokens()) {
			String token2 = tok.nextToken();
			if (InjuryCure.isHealed(token2)) {
				healed = true;
			} else {
				cause = InjuryCause.valueOf(token2);

				if (tok.hasMoreTokens() && InjuryCure.isHealed(tok.nextToken())) {
					healed = true;
				}
			}
		}

		if (type.size() > 0
				&& (type.get(0) == InjuryType.AC || type.get(0) == InjuryType.RETIRED)) {
			cause = InjuryCause.Other;
		}
	}

	public Injury(List<InjuryType> type) {
		this(type, InjuryCause.Block, InjuryCure.None, false);
	}

	public Injury(List<InjuryType> type, InjuryCause cause, InjuryCure cure,
			boolean healed) {
		this.type = type;
		this.cause = cause;
		this.cure = cure;
		this.healed = healed;
	}

	public static List<Injury> parseInjury(String inj) {
		ArrayList<Injury> injList = new ArrayList<Injury>();

		StringTokenizer injTok = new StringTokenizer(inj, INJ_SEPARATOR);
		while (injTok.hasMoreTokens()) {
			String nextInj = injTok.nextToken().trim();
			if (nextInj.length() > 0) {
				Injury newInj = new Injury(nextInj);
				injList.add(newInj);
			}
		}

		return injList;
	}

	public String toString() {
		return getType() + INJ_TOKEN_SEPARATOR + getCause()
				+ (isHealed() ? getCure() : "");
	}

	/**
	 * Getter for property type.
	 * 
	 * @return Value of property type.
	 */
	public String getType() {
		StringBuffer res = new StringBuffer();
		Iterator<InjuryType> it = type.iterator();
		while (it.hasNext()) {
			InjuryType t = it.next();
			res.append(t.toString() + (it.hasNext() ? INJ_TYPE_SEPARATOR : ""));
		}
		return res.toString();
	}

	/**
	 * Getter for property cause.
	 * 
	 * @return Value of property cause.
	 */
	public String getCause() {
		String res = null;

		if (cause == null) {
			res = "";
		} else {
			res = cause.toString();
		}

		return res;
	}

	/**
	 * @return Returns the cure.
	 */
	public String getCure() {
		return cure.toString();
	}

	/**
	 * Getter for property healed.
	 * 
	 * @return Value of property healed.
	 */
	public boolean isHealed() {
		return healed;
	}

	public boolean contains(InjuryType type) {
		boolean res = false;
		for (InjuryType t : this.type) {
			if (t == type) {
				res = true;
				break;
			}
		}
		return res;
	}

	public static void main(String[] args) {
		List<Injury> injs = Injury.parseInjury("N+MNG Crowd; AC");

		for (Injury inj : injs) {
			System.out.println(inj.toString());
		}
	}

}