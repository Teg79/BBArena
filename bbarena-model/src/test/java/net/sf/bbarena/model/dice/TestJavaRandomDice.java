package net.sf.bbarena.model.dice;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cammerucci
 *
 */
public class TestJavaRandomDice extends TestCase{
	
	private static final Logger log = LoggerFactory.getLogger(TestJavaRandomDice.class);
	private JavaRandomizer d;
	
	public void setUp() throws Exception {
		d = new JavaRandomizer();
	}
	
	//	 ------------------ TESTS -----------------------------
	
	public void testSixJavaRandomDice(){
		for(int i=0; i<10000; ++i) {
			int res = d.getRollFace(6, "");
//			log.info("Roll d6: " + res);
			assertTrue(res >= 1 && res <= 6);
		}
	}
	
	public void testEightJavaRandomDice(){
		for(int i=0; i<10000; ++i) {
			int res = d.getRollFace(8, "");
//			log.info("Roll d8: " + res);
			assertTrue(res >= 1 && res <= 8);
		}
	}
}
