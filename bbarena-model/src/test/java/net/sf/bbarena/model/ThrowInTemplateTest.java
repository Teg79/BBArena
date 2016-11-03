package net.sf.bbarena.model;

import static org.junit.Assert.fail;
import net.sf.bbarena.model.pitch.Pitch;
import net.sf.bbarena.model.team.Team;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class ThrowInTemplateTest {

	private static final Logger log = LoggerFactory.getLogger(ThrowInTemplateTest.class);

	private ThrowInTemplate tit = null;

	private Coordinate[] coords = null;

	@Before
	public void setUp() throws Exception {
		// Prepare the teams
		Team t1 = new Team(1L, null);
		t1.setName("1");
		Team t2 = new Team(2L, null);
		t2.setName("2");

		Coach c1 = new DummyCoach("C1", t1);
		Coach c2 = new DummyCoach("C2", t2);

		Match match = new Match(c1, c2);

		Arena arena = match.getArena();
		Pitch p = arena.getPitch();

		coords = new Coordinate[] { new Coordinate(0, 0),
				new Coordinate(0, p.getHeight() / 2),
				new Coordinate(0, p.getHeight() - 1),
				new Coordinate(p.getWidth() / 2, 0),
				new Coordinate(p.getWidth() - 1, 0),
				new Coordinate(p.getWidth() - 1, p.getHeight() - 1) };

		this.tit = new ThrowInTemplate(arena);
	}

	@Test
	public void testThrowInDirection() {
		try {
			for (Coordinate c : coords) {
				log.info("Coordinate: " + c.toString());
				for (int i = 1; i <= 6; i++) {
					log.info(tit.getThrowInDirection(i, c) + " ");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			fail("FAILED!!!");
		}
	}

}
