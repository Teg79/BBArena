package bbarena.rules.crap;

import de.vandermeer.asciitable.v2.RenderedTable;
import de.vandermeer.asciitable.v2.V2_AsciiTable;
import de.vandermeer.asciitable.v2.render.V2_AsciiTableRenderer;
import de.vandermeer.asciitable.v2.render.WidthAbsoluteEven;
import de.vandermeer.asciitable.v2.themes.V2_E_TableThemes;
import bbarena.model.Coordinate;
import bbarena.model.Match;
import bbarena.model.Roll;
import bbarena.model.choice.Continue;
import bbarena.model.choice.FlipRoll;
import bbarena.model.dice.DieRandomizer;
import bbarena.model.pitch.Pitch;
import bbarena.model.pitch.Square;
import bbarena.model.pitch.TeamSetUp;
import bbarena.model.team.Player;
import bbarena.model.team.Team;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class TestCrap {

    private static final Logger _log = LoggerFactory.getLogger(TestCrap.class);

    @Test
    public void testMatch() {
        Team t1 = Factory.buildTeam();
        Team t2 = Factory.buildTeam();

        Iterator<Player> p1Iterator = t1.getPlayers().iterator();
        SimCoach c1 = new SimCoach("C1", t1,
                FlipRoll.KICK,
                p1Iterator.next(),
                new Square(new Coordinate(12, 6)),
                p1Iterator.next(),
                new Square(new Coordinate(12, 7)),
                p1Iterator.next(),
                new Square(new Coordinate(12, 8)),
                p1Iterator.next(),
                new Square(new Coordinate(10, 2)),
                p1Iterator.next(),
                new Square(new Coordinate(10, 5)),
                p1Iterator.next(),
                new Square(new Coordinate(10, 9)),
                p1Iterator.next(),
                new Square(new Coordinate(10, 12)),
                p1Iterator.next(),
                new Square(new Coordinate(9, 1)),
                p1Iterator.next(),
                new Square(new Coordinate(9, 4)),
                p1Iterator.next(),
                new Square(new Coordinate(9, 10)),
                p1Iterator.next(),
                new Square(new Coordinate(9, 13)),
                new Continue());
        Iterator<Player> p2Iterator = t2.getPlayers().iterator();
        SimCoach c2 = new SimCoach("C2", t2,
                p2Iterator.next(),
                new Square(new Coordinate(13, 6)),
                p2Iterator.next(),
                new Square(new Coordinate(13, 7)),
                p2Iterator.next(),
                new Square(new Coordinate(13, 8)),
                p2Iterator.next(),
                new Square(new Coordinate(15, 2)),
                p2Iterator.next(),
                new Square(new Coordinate(15, 5)),
                p2Iterator.next(),
                new Square(new Coordinate(15, 9)),
                p2Iterator.next(),
                new Square(new Coordinate(15, 12)),
                p2Iterator.next(),
                new Square(new Coordinate(16, 1)),
                p2Iterator.next(),
                new Square(new Coordinate(16, 4)),
                p2Iterator.next(),
                new Square(new Coordinate(16, 10)),
                p2Iterator.next(),
                new Square(new Coordinate(16, 13)),
                new Continue());

        Match<Crap> match = new Match<>(c1, c2);
        Pitch pitch = match.getArena().getPitch();

        c1.addAnswer(new Square(pitch, new Coordinate(15, 2)));

        DieRandomizer roller = new SimGenerator(
                null, null, // Weather
                null, null, // FAME c1
                null, null, // FAME c2
                1, // Flip Coin, c1 wins
                8, 3, // Bounce W for 8
                4, 3, // Kick Off Event
                5, // Fail catch
                6, // Scatter
                5, // Catch
                5 // Scatter
        );

        Roll.setGenerator(roller);

        match.start(new Crap());

        printPitch(pitch);
    }

    public static void printPitch(Pitch pitch) {
        V2_AsciiTable table = new V2_AsciiTable();
        table.addRule();
        table.addRow(" ", "||", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, "12|", "|13", 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, "||");
        table.addRule();
        for (int i = 0; i < pitch.getHeight(); i++) {
            Object[] row = new Object[pitch.getWidth() + 1];
            row[0] = i;
            for (int x = 0; x < pitch.getWidth(); x++) {
                Square square = pitch.getSquare(new Coordinate(x, i));
                row[x + 1] = square.hasBall() ? "O" : square.hasPlayer() ? square.getPlayer().hasBall() ? "XO" : "X" : " ";
            }
            table.addRow(row);
            table.addRule();
        }

        V2_AsciiTableRenderer renderer = new V2_AsciiTableRenderer();
        renderer.setTheme(V2_E_TableThemes.UTF_STRONG_DOUBLE.get());
        renderer.setWidth(new WidthAbsoluteEven(160));
        RenderedTable rt = renderer.render(table);
        System.out.println(rt);
    }

    private TeamSetUp prepareSetUpHome(Team team) {
        TeamSetUp res = new TeamSetUp();
        Iterator<Player> playerIterator = team.getPlayers().iterator();
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(12, 6));
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(12, 7));
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(12, 8));
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(10, 2));
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(10, 5));
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(10, 9));
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(10, 12));
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(9, 1));
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(9, 4));
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(9, 10));
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(9, 13));

        return res;
    }

    private TeamSetUp prepareSetUpAway(Team team) {
        TeamSetUp res = new TeamSetUp();
        Iterator<Player> playerIterator = team.getPlayers().iterator();
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(13, 6));
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(13, 7));
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(13, 8));
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(15, 2));
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(15, 5));
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(15, 9));
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(15, 12));
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(16, 1));
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(16, 4));
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(16, 10));
        res.getSetUp().put(playerIterator.next().getId(), new Coordinate(16, 13));

        return res;
    }

}
