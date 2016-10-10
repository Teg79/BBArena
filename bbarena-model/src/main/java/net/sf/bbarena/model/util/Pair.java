package net.sf.bbarena.model.util;

public class Pair {
	
	private String _name;
	private String _value;
	
	public Pair(String name, String value) {
		_name = name;
		_value = value;
	}

	public Pair(String name, Object value) {
		this(name, (value == null ? "" : value.toString()));
	}

	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append(_name)
			.append(":")
			.append(_value);
		return res.toString();
	}
	
}
