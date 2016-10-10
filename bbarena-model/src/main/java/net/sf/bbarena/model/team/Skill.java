package net.sf.bbarena.model.team;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Every Skill is a singleton and must be obtained with the method
 * getSkill(name)
 *
 * @author f.bellentani
 */
public class Skill implements Serializable, Comparable<Skill> {

	private static final long serialVersionUID = 3580068957083370543L;

	private static Map<String, Skill> skills = new HashMap<String, Skill>();

	private String name = null;

	private Skill(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public static Skill getSkill(String skillName) {
		Skill res = skills.get(skillName);
		if (res == null) {
			res = new Skill(skillName);
			skills.put(skillName, res);
		}

		return res;
	}

	public int compareTo(Skill o) {
		return this.name.compareTo(o.name);
	}

}
