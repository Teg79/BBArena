package net.sf.bbarena.model;

import java.util.Iterator;
import java.util.List;

import net.sf.bbarena.model.RangeRuler.Range;
import net.sf.bbarena.model.exception.PitchException;
import net.sf.bbarena.model.pitch.Pitch;
import net.sf.bbarena.model.pitch.PitchFactory;
import net.sf.bbarena.model.team.Player;
import net.sf.bbarena.model.team.Team;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author f.bellentani
 * 
 */
public class TestRangeRuler {

	private static final Logger log = LoggerFactory.getLogger(TestPitch.class);

	private RangeRuler ruler = null;

	private Pitch pitch = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		// Prepare the ruler
		Team t1 = new Team(1L, null);
		t1.setName("1");
		Team t2 = new Team(2L, null);
		t2.setName("2");

		pitch = PitchFactory.getPitch(t1, t2);
		ruler = new RangeRuler(pitch);

		// Put players on the pitch
		for (int i = 0; i < pitch.getHeight() * pitch.getWidth(); i++) {
			t1.addPlayer(new Player(i, i, "" + i));
		}

		Iterator<Player> players = t1.getPlayers().iterator();
		for (int x = 0; x < pitch.getWidth(); x++) {
			for (int y = 0; y < pitch.getHeight(); y++) {
				Player p = players.next();
				try {
					pitch.putPlayer(p, new Coordinate(x, y));
				} catch (PitchException e) {
				}
			}
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.bbarena.model.RangeRuler#getRange(net.sf.bbarena.model.Coordinate, net.sf.bbarena.model.Coordinate)}.
	 */
	@Test
	public void testGetRange() {
		Coordinate from = new Coordinate(5, 5);
		for (int x = 0; x < pitch.getWidth(); x++) {
			StringBuilder line = new StringBuilder();
			for (int y = 0; y < pitch.getHeight(); y++) {
				Range range = ruler.getRange(from, new Coordinate(x, y));
				line.append(range.getSymbol()).append(" ");
			}
			log.info(line.toString());
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.bbarena.model.RangeRuler#getInterceptionPlayers(net.sf.bbarena.model.Coordinate, net.sf.bbarena.model.Coordinate)}.
	 */
	@Test
	public void testGetInterceptionSquares() {
		Coordinate from = new Coordinate(6, 6);
		Coordinate to = new Coordinate(16, 16);
		List<Player> players = ruler.getInterceptionPlayers(from, to);

		// Print the pitch
		for (int x = 0; x < pitch.getWidth(); x++) {
			StringBuilder line = new StringBuilder();
			for (int y = 0; y < pitch.getHeight(); y++) {
				Player p = pitch.getSquare(new Coordinate(x, y)).getPlayer();
				String name = "";
				if (players.contains(p)) {
					name = " X ";
				} else {
					name = (p == null ? " - " : p.getName());
				}
				if (name.length() < 3) {
					line.append(" ");
				}
				if (name.length() < 2) {
					line.append(" ");
				}
				line.append(name).append(" ");
			}
			log.info(line.toString());
		}
	}

}
