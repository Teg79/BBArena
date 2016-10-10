package net.sf.bbarena.model;

import java.util.UUID;

import net.sf.bbarena.model.dice.RandomOrgRandomizer;
import net.sf.bbarena.model.event.Die;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Roll {
	
	private static final Logger _log = LoggerFactory.getLogger(Roll.class);
	private String _id;
	private Integer _number;
	private Die _type;
	private final Integer[] _results;
	
	public Roll(Integer number, Die type, RandomOrgRandomizer generator) {
		_id = UUID.randomUUID().toString();
		_number = number;
		_type = type;
		_results = new Integer[_number];
		for (int i = 0; i < _number; i++) {
			Integer resultAggregation = 0;
			for (int j = _type.getFaces().length - 1; j >= 0 ; j--) {
				Integer die = _type.getFaces()[j];
				Integer result = generator.getRollFace(die, _id);
				_log.debug("Roll " + _id + ": " + result + "/" + type.getFaces()[j]);
				resultAggregation = resultAggregation + (result * (int)Math.pow(10, _type.getFaces().length - 1 - j));
			}
			_results[i] = resultAggregation;
		}
	}
	
	public Integer[] getResults() {
		return _results;
	}

	public Integer getNumber() {
		return _number;
	}

	public Die getType() {
		return _type;
	}
	
	public String getId() {
		return _id;
	}
	
}
