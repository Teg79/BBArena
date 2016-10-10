package net.sf.bbarena.model.team;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author f.bellentani
 */
public class Attributes implements Serializable {
	
	private static final long serialVersionUID = 9010624429104767653L;

	public enum Attribute {
		MA, ST, AG, AV;
		
		public static Attribute parseAttribute(String attribute) {
			Attribute res = null;
			String a = attribute.trim().substring(0, 2).toUpperCase();
			if(a.equals(MA.toString())) {
				res = MA;
			} else if(a.equals(ST.toString())) {
				res = ST;
			} else if(a.equals(AG.toString())) {
				res = AG;
			} else if(a.equals(AV.toString())) {
				res = AV;
			}
			
			return res;
		}
	}
	
	private Map<Attribute, Integer> attributeMap;
	
	public Attributes() {
		attributeMap = new HashMap<Attribute, Integer>(4);
	}
	
	public Attributes(int ma, int st, int ag, int av) {
		this();
		attributeMap.put(Attribute.MA, ma);
		attributeMap.put(Attribute.ST, st);
		attributeMap.put(Attribute.AG, ag);
		attributeMap.put(Attribute.AV, av);
	}

	public int getAttribute(Attribute attribute) {
		int res = attributeMap.get(attribute);
		return res;
	}
	
}
