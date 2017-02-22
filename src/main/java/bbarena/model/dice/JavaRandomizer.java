package bbarena.model.dice;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * 
 * @author cammerucci
 *
 */
public class JavaRandomizer implements DieRandomizer{

	private final Random _random = new SecureRandom();
	private final Map<String, Integer> _results;
	
	public JavaRandomizer() {
		_results = new HashMap<>();
	}

	public int getRollFace(int facesNumber) {
		return getRollFace(facesNumber, null);
	}

	public int getRollFace(int facesNumber, String id) {
		Integer res = null;

		if (id != null) {
			res = _results.get(id);
		}
		if (res == null) {
			res = _random.nextInt(facesNumber - 1) + 1;
			if (id != null) {
				_results.put(id, res);
			}
		}
		
		return res;
	}
}
