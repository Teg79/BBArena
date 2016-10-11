package net.sf.bbarena.model;

import net.sf.bbarena.model.exception.PitchException;
import net.sf.bbarena.model.pitch.Pitch;
import net.sf.bbarena.model.pitch.PitchFactory;
import net.sf.bbarena.model.team.Player;
import net.sf.bbarena.model.team.Team;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class TestPitch {

    private static final Logger log = LoggerFactory.getLogger(TestPitch.class);

    Pitch pitch = null;

    @Before
    public void setUp() throws Exception {

        // Prepare the pitch
        Team t1 = new Team(1L, null);
        t1.setName("1");
        Team t2 = new Team(2L, null);
        t2.setName("2");

        pitch = PitchFactory.getPitch(t1, t2);

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
//
//	@Test
//	public void testPitch() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetSquares() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAddDugout() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetPitchName() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetWidth() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetHeight() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetSquare() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetTZCount() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetNextSquare() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testMovePlayerPlayerDirection() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testMovePlayerPlayerDirectionInt() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testMoveBallBallDirection() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testMoveBallBallDirectionInt() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testPutPlayerPlayerCoordinate() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testPutBall() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRemoveBall() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testPutPlayerPlayerDugoutRoom() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetDugouts() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetDugout() {
//		fail("Not yet implemented");
//	}

    @Test
    public void testGetPlayersInRange() {
        List<Player> players = pitch.getPlayersInRange(new Coordinate(24, 13), 3);

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
