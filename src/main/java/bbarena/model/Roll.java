package bbarena.model;

import bbarena.model.dice.DieRandomizer;
import bbarena.model.dice.RandomOrgRandomizer;
import bbarena.model.event.Die;
import bbarena.model.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Roll {
	
	private static final Logger _log = LoggerFactory.getLogger(Roll.class);

	private static DieRandomizer GENERATOR = RandomOrgRandomizer.getInstance();

	public static void setGenerator(DieRandomizer generator) {
		GENERATOR = generator;
	}

	public static RollResult roll(Integer dice, Die type, Event e, String why, String who) {
		Roll roll = new Roll(dice, type, GENERATOR);
		int[] results = roll.getResults();
		RollResult rollResult = new RollResult(roll, results, why, who);
        e.addDiceRoll(rollResult);
		return rollResult;
	}

	private String _id;
	private Integer _dice;
	private Die _type;
	private final int[] _results;

	public Roll(Integer dice, Die type, DieRandomizer generator) {
		_id = UUID.randomUUID().toString();
		_dice = dice;
		_type = type;
		_results = new int[_dice];
		for (int i = 0; i < _dice; i++) {
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
	
	public int[] getResults() {
		return _results;
	}

	public Integer getDice() {
		return _dice;
	}

	public Die getType() {
		return _type;
	}
	
	public String getId() {
		return _id;
	}
	
}
