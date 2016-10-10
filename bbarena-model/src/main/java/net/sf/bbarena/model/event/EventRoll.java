package net.sf.bbarena.model.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.bbarena.model.Roll;

/**
 * This class is the representation of a dice roll for a particular action.
 * 
 * @author f.bellentani
 */
public class EventRoll implements Serializable {

	private static final long serialVersionUID = 2056992995457851150L;

	private String _rollType = null;
	private List<Roll> _dice = new ArrayList<Roll>();
	private List<RollModifier> _modifiers = new ArrayList<RollModifier>();

	public EventRoll(String rollType) {
		_rollType = rollType;
	}

	public void addRoll(Roll roll) {
		_dice.add(roll);
	}

	public String getRollType() {
		return _rollType;
	}

	public void setRollType(String rollType) {
		_rollType = rollType;
	}

	public List<Roll> getDice() {
		return _dice;
	}

	public void addModifier(int modifier, String description) {
		RollModifier mod = new RollModifier(modifier, description);
		_modifiers.add(mod);
	}

	public List<RollModifier> getModifiers() {
		return _modifiers;
	}

}
