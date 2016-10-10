package net.sf.bbarena.model.dice;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author cammerucci
 *
 */
public class JavaRandomizer implements DieRandomizer{
	
	private final Map<String, Integer> _results;
	
	public JavaRandomizer() {
		_results = new HashMap<String, Integer>();
	}
	
	public int getRollFace(int facesNumber, String id) {
		Integer res = _results.get(id);
		if (res == null) {
			res = (int)(Math.random() * facesNumber) + 1;
			_results.put(id, res);
		}
		
		return res;
	}
}
