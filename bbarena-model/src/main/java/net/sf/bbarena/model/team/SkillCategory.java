package net.sf.bbarena.model.team;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author f.bellentani
 *
 */
public class SkillCategory {

	private char id;
	private String name;
	private String description;
	private Set<Skill> skills;

	public SkillCategory() {

	}
	
	public SkillCategory(char id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
		
		this.skills = new TreeSet<Skill>();
	}
	
	public boolean addSkill(Skill skill) {
		boolean res;
		res = this.skills.add(skill);
		
		return res;
	}
	
	public Set<Skill> getSkills() {
		return skills;
	}
	
	public String toString() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public char getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public static String toString(Collection<SkillCategory> categories) {
		StringBuffer res = new StringBuffer("");
		for(SkillCategory sc : categories) {
			res.append(sc.getId());
		}
		
		return res.toString().toUpperCase();
	}
	
	public boolean hasSkill(Skill skill) {
		return skills.contains(skill);
	}
	
}
