package bbarena.model;

import bbarena.model.pitch.Pitch;
import bbarena.model.pitch.Square;
import bbarena.model.rules.bb.PitchFactory;
import bbarena.model.team.Team;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPitchFactory {

    private static final Logger log = LoggerFactory.getLogger(TestPitchFactory.class);

    @Test
    public void testPitchFactory() {
        log.info("Start...");
        Team t1 = new Team(1L, null);
        t1.setName("1");
        Team t2 = new Team(2L, null);
        t2.setName("2");
        Pitch p = PitchFactory.Companion.getPitch(t1, t2);
        log.debug("Pitch created");
        for(int x = 0; x < p.getWidth(); x++) {
            StringBuilder line = new StringBuilder();
            for(int y = 0; y < p.getHeight(); y++) {
                Square s = p.getSquare(new Coordinate(x, y));
                Team t = s.getTeamOwner();
                String team = (t == null ? "0" : t.getName());
                line.append(s.getType().toString()).append(team);
            }
            log.info(line.toString());
        }
        log.info("Done!");
    }

}
