package net.sf.bbarena.model;


import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class DirectionTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNext() {
		Direction dir = Direction.E;
		for(int i = 0; i < 9; i++) {
			System.out.println(dir);
			dir = dir.next();
		}
		if(dir != Direction.SE) {
			fail("FAILED!!!");
		}
	}

	@Test
	public void testNextSteps() {
		Direction dir = Direction.E;
		for(int i = 0; i < 9; i++) {
			System.out.println(dir);
			dir = dir.next(2);
		}
		if(dir != Direction.S) {
			fail("FAILED!!!");
		}
	}

}
