package net.sf.bbarena.model.dice;

import net.sf.bbarena.model.Roll;
import net.sf.bbarena.model.event.Die;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class TestRoll {

	private static final Logger log = LoggerFactory.getLogger(TestRoll.class);

	@Test
	public void testRoll() {
		for (int i = 0; i < 100; i++) {
			Roll roll = new Roll(1, Die.D68, RandomOrgRandomizer.getInstance());
			log.info(roll.getResults()[0] + " ");
			int res = roll.getResults()[0];
			assertTrue(
					(res >= 11 && res <= 18) ||
					(res >= 21 && res <= 28) ||
					(res >= 31 && res <= 38) ||
					(res >= 41 && res <= 48) ||
					(res >= 51 && res <= 58) ||
					(res >= 61 && res <= 68));
		}
		for (int i = 0; i < 100; i++) {
			Roll roll = new Roll(1, Die.D6, RandomOrgRandomizer.getInstance());
			log.info(roll.getResults()[0] + " ");
			int res = roll.getResults()[0];
			assertTrue(res >= 1 && res <= 6);
		}
	}
	
}
