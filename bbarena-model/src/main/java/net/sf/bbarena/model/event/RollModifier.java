package net.sf.bbarena.model.event;

import java.io.Serializable;

public class RollModifier implements Serializable {

	private static final long serialVersionUID = -4692545546332381896L;

	private final int _modifier;
	private final String _description;

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

	@Override
	public String toString() {
		return new StringBuilder(_modifier).append(" (").append(_description).append(")").toString();
	}

}
