/**
 *
 */
package bbarena.model.team;

import java.io.Serializable;
import java.util.List;


public class PlayerTemplate implements Serializable {

	private static final long serialVersionUID = 6807201986807754711L;

	private Roster roster;
	private String race;
	private Qty quantity;
	private String position;
	private long cost;
	private Attributes attributes;
	private List<Skill> skills;
	private List<SkillCategory> normal;
	private List<SkillCategory> doubles;

	public PlayerTemplate() {

	}

	public PlayerTemplate(int min, int max, String position, long cost, int ma, int st, int ag, int av, List<Skill> skills, List<SkillCategory> normal, List<SkillCategory> doubles) {
		this.quantity = new Qty(min, max);
		this.position = position;
		this.cost = cost;
		this.attributes = new Attributes(ma, st, ag, av);
		this.skills = skills;
		this.normal = normal;
		this.doubles = doubles;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public long getCost() {
		return cost;
	}

	public List<SkillCategory> getDoubles() {
		return doubles;
	}

	public String getDoublesString() {
		String res = SkillCategory.Companion.toString(getDoubles());
		return res;
	}

	public List<SkillCategory> getNormal() {
		return normal;
	}

	public String getNormalString() {
		String res = SkillCategory.Companion.toString(getNormal());
		return res;
	}

	public String getPosition() {
		return position;
	}

	public Qty getQuantity() {
		return quantity;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public String getSkillsString() {
		StringBuffer res = new StringBuffer("");

		List<Skill> skills = getSkills();
		for(int i = 0; i < skills.size(); i++) {
			Skill skill = skills.get(i);
			res.append(skill.getName());
			if( i < (skills.size()-1) ) {
				res.append(", ");
			}
		}
		return res.toString();
	}

	public String toString() {
		return getQuantity().toString() + " " + getPosition() + " " + getAttributes().toString() + " " + getSkillsString() + " " + getNormalString() + " " + getDoublesString();
	}

	public Roster getRoster() {
		return roster;
	}

	protected void setRoster(Roster roster) {
		this.roster = roster;
	}

	public boolean isDouble(Skill skill) {
		return isInCategories(doubles, skill);
	}

	public boolean isNormal(Skill skill) {
		return isInCategories(normal, skill);
	}

	private boolean isInCategories(List<SkillCategory> categories, Skill skill) {
		boolean res = false;
		for(SkillCategory category : categories) {
			if(category.hasSkill(skill)) {
				res = true;
				break;
			}
		}
		return res;
	}

	public String getRace() {
		return race;
	}

	protected void setRace(String race) {
		this.race = race;
	}

}