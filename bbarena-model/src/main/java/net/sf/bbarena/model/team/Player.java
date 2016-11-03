package net.sf.bbarena.model.team;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import net.sf.bbarena.model.Choice;
import net.sf.bbarena.model.pitch.Ball;
import net.sf.bbarena.model.pitch.Square;
import net.sf.bbarena.model.team.Attributes.Attribute;

/**
 * @author f.bellentani
 *
 */
public class Player implements Choice, Serializable {

	private static final long serialVersionUID = 6210061039167056132L;

	public enum PlayerType {
		NORMAL, STAR, JOURNEYMAN, MERCENARY
	}

	public enum PlayerPitchStatus {
		STANDING, PRONE, STUNNED;

		public static PlayerPitchStatus getPitchStatus(int pitchStatus) {
			PlayerPitchStatus res = null;
			if(pitchStatus >= 1 && pitchStatus <= 8) {
				res = PlayerPitchStatus.values()[pitchStatus];
			}
			return res;
		}
	}

	private PlayerTemplate template = null;

	private long id = 0;

	private int num = 0;

	private String name = "";

	private Team team = null;

	private Experience experience = new Experience();

	private Experience newExperience = null;

	private List<Skill> skills = new ArrayList<Skill>();

	private List<AttributeModifier> attributesModifiers = new ArrayList<AttributeModifier>();

	private String injury = "";

	private boolean mng = false;

	private int nigglings = 0;

	private PlayerType type = null;

	private PlayerPitchStatus pitchStatus = null;

	private boolean tz = true;

	private boolean moved = false;

	private Ball ball = null;

	private Square square = null;

	private boolean dugout = true;

	/**
	 * A player can be used in the Kick-Off set up only if this is true
	 */
	private boolean playable = true;

	/** Creates a new instance of Player */
	public Player(long id, int num, String name) {
		this(id, num, name, null, null, null, null);
	}

	public Player(long id, int num, String name, PlayerTemplate template,
			Experience experience, List<Skill> skills,
			List<AttributeModifier> attributesModifiers) {
		this.id = id;
		this.template = template;
		this.num = num;
		this.name = name;
		if (experience != null) {
			this.experience = experience;
		}
		this.newExperience = new Experience();
		if (skills != null) {
			this.skills = skills;
		}
		if (attributesModifiers != null) {
			this.attributesModifiers = attributesModifiers;
		}
		this.type = PlayerType.NORMAL;
		this.pitchStatus = PlayerPitchStatus.STANDING;
	}

	public boolean equals(Object o) {
		Player p = (Player) o;
		return getTeam().equals(p.getTeam()) && getNum() == p.getNum()
				&& this.template == p.template;
	}

	public String getAllSkills() {
		StringBuffer skills = new StringBuffer();
		if (getSkills() != null && getSkills().size() > 0) {
			skills.append(getSkillsString());
		}
		if (getAttributesModifiers() != null
				&& getAttributesModifiers().size() > 0) {
			if (skills.length() > 0) {
				skills.append(", ");
			}
			skills.append(getAttributesString());
		}

		return skills.toString();
	}

	private int getAttributeModifier(Attribute attribute) {
		int res = 0;
		for (int i = 0; i < attributesModifiers.size(); i++) {
			AttributeModifier a = (AttributeModifier) attributesModifiers
					.get(i);
			if (a.getType() == attribute) {
				res += a.getMod();
			}
		}
		return res;
	}

	/**
	 * Getter for property num.
	 *
	 * @return Value of property num.
	 *
	 */
	public int getNum() {
		return num;
	}

	/**
	 * Setter for property num.
	 *
	 * @param num
	 *            New value of property num.
	 *
	 */
	protected void setNum(int num) {
		this.num = num;
	}

	/**
	 * Getter for property name.
	 *
	 * @return Value of property name.
	 *
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for property name.
	 *
	 * @param name
	 *            New value of property name.
	 *
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for property position.
	 *
	 * @return Value of property position.
	 *
	 */
	public String getPosition() {
		return template.getPosition();
	}

	/**
	 * Getter for property ma.
	 *
	 * @return Value of property ma.
	 *
	 */
	public int getMa() {
		return template.getAttributes().getAttribute(Attribute.MA)
				+ getAttributeModifier(Attribute.MA);
	}

	/**
	 * Getter for property st.
	 *
	 * @return Value of property st.
	 *
	 */
	public int getSt() {
		return template.getAttributes().getAttribute(Attribute.ST)
				+ getAttributeModifier(Attribute.ST);
	}

	/**
	 * Getter for property ag.
	 *
	 * @return Value of property ag.
	 *
	 */
	public int getAg() {
		return template.getAttributes().getAttribute(Attribute.AG)
				+ getAttributeModifier(Attribute.AG);
	}

	/**
	 * Getter for property av.
	 *
	 * @return Value of property av.
	 *
	 */
	public int getAv() {
		return template.getAttributes().getAttribute(Attribute.AV)
				+ getAttributeModifier(Attribute.AV);
	}

	public void addSkill(Skill skill) {
		this.skills.add(skill);
	}

	/**
	 * Getter for property skills.
	 *
	 * @return Value of property skills.
	 *
	 */
	public List<Skill> getSkills() {
		return new ArrayList<Skill>(this.skills);
	}

	/**
	 * Setter for property skills.
	 *
	 * @param skills
	 *            New value of property skills.
	 *
	 */
	public void setSkills(List<Skill> skills) {
		this.skills = new ArrayList<Skill>(skills);
	}

	public String getSkillsString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < skills.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(skills.get(i).getName());
		}
		return sb.toString();
	}

	public void setSkillsString(String skills) {
		List<String> stringSkills = tokenizeString(skills);
		List<Skill> listskills = new ArrayList<Skill>();
		for (String skill : stringSkills) {
			listskills.add(Skill.getSkill(skill));
		}
		setSkills(listskills);
	}

	private List<String> tokenizeString(String s) {
		ArrayList<String> list = new ArrayList<String>();

		StringTokenizer tok = new StringTokenizer(s, ",");
		while (tok.hasMoreTokens()) {
			String t = tok.nextToken().trim();
			if (t.length() > 0) {
				list.add(t);
			}
		}

		return list;
	}

	public void addAttributeModifier(AttributeModifier attribute) {
		this.attributesModifiers.add(attribute);
	}

	/**
	 * Getter for property attributes.
	 *
	 * @return Value of property attributes.
	 *
	 */
	public List<AttributeModifier> getAttributesModifiers() {
		return this.attributesModifiers;
	}

	/**
	 * Setter for property attributes.
	 *
	 * @param attributes
	 *            New value of property attributes.
	 *
	 */
	public void setAttributesModifiers(List<AttributeModifier> attributes) {
		this.attributesModifiers = attributes;
	}

	public String getAttributesString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < attributesModifiers.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(((AttributeModifier) attributesModifiers.get(i))
					.toString());
		}
		return sb.toString();
	}

	public void setAttributesString(String attributes) {
		List<String> attNames = tokenizeString(attributes);

		List<AttributeModifier> res = new ArrayList<AttributeModifier>(attNames
				.size());

		for (String att : attNames) {
			res.add(new AttributeModifier(att));
		}

		setAttributesModifiers(res);
	}

	/**
	 * Getter for property status.
	 *
	 * @return Value of property status.
	 *
	 */
	public PlayerType getType() {
		PlayerType res = null;
		if (type == null) {
			res = PlayerType.NORMAL;
		} else {
			res = type;
		}

		return res;
	}

	/**
	 * Setter for property status.
	 *
	 * @param status
	 *            New value of property status.
	 *
	 */
	public void setType(PlayerType type) {
		this.type = type;
	}

	/**
	 * Getter for property team.
	 *
	 * @return Value of property team.
	 *
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Setter for property team.
	 *
	 * @param team
	 *            New value of property team.
	 *
	 */
	protected void setTeam(Team team) {
		this.team = team;
	}

	/**
	 * Getter for property race.
	 *
	 * @return Value of property race.
	 */
	public String getRace() {
		String res = null;

		if (team != null) {
			res = team.getRace();
		}
		return res;
	}

	/**
	 * Getter for property nigglings.
	 *
	 * @return Value of property nigglings.
	 */
	public int getNigglings() {
		return nigglings;
	}

	/**
	 * Setter for property nigglings.
	 *
	 * @param nigglings
	 *            New value of property nigglings.
	 */
	public void setNigglings(int nigglings) {
		this.nigglings = nigglings;
	}

	/**
	 * Check if the player's Tackle Zones are active
	 *
	 * @return true if TZ are active
	 */
	public boolean hasTZ() {
		return tz;
	}

	/**
	 * Turns on or off the player's Tackle Zones
	 *
	 * @param tz
	 *            true to activate, false to deactivate
	 */
	public void activateTZ(boolean tz) {
		this.tz = tz;
	}

	/**
	 * @return the injury
	 */
	public String getInjury() {
		return injury;
	}

	/**
	 * @param injury
	 *            the injury to set
	 */
	public void setInjury(String injury) {
		this.injury = injury;
	}

	public Experience getExperience() {
		return experience;
	}

	public Experience getNewExperience() {
		return newExperience;
	}

	public long getCost() {
		long res = 0;

		if(template != null) {
			res = template.getCost();
		}

		return res;
	}

	public boolean isMng() {
		return mng;
	}

	public void setMng(boolean mng) {
		this.mng = mng;
	}

	public long getValue() {
		long res = 0;

		if (!isMng()) {
			res += getCost();
			res += 50000 * getAttributeModifier(Attribute.ST);
			res += 40000 * getAttributeModifier(Attribute.AG);
			res += 30000 * getAttributeModifier(Attribute.AV);
			res += 30000 * getAttributeModifier(Attribute.MA);
			for (Skill skill : skills) {
				if (template.isNormal(skill)) {
					res += 20000;
				} else if (template.isDouble(skill)) {
					res += 30000;
				}
			}
		}
		return res;
	}

	public Square getSquare() {
		return square;
	}

	public void setSquare(Square square) {
		this.square = square;
	}

	public boolean isInDugout() {
		return dugout;
	}

	public void setInDugout(boolean dugout) {
		this.dugout = dugout;
	}

	public boolean hasMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}

	public boolean hasBall() {
		return this.ball != null;
	}

	public void removeBall() {
		this.ball = null;
	}

	public long getId() {
		return id;
	}

	public PlayerPitchStatus getPitchStatus() {
		return pitchStatus;
	}

	public void setPitchStatus(PlayerPitchStatus pitchStatus) {
		this.pitchStatus = pitchStatus;
	}

	public boolean isPlayable() {
		return playable;
	}

	public void setPlayable(boolean playable) {
		this.playable = playable;
	}

	@Override
	public String toString() {
		return getNum() + " " + getName();
	}

}
