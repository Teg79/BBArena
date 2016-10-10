package net.sf.bbarena.model.event;

import java.io.Serializable;

public class RollModifier implements Serializable {

	private static final long serialVersionUID = -4692545546332381896L;

	private int _modifier = 0;
	private String _description = "";

	public RollModifier(int modifier, String description) {
		_modifier = modifier;
		_description = description;
	}

	public String getDescription() {
		return _description;
	}

	public int getModifier() {
		return _modifier;
	}

}
