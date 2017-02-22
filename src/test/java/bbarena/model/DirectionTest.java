package bbarena.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DirectionTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testNext() {
        Direction dir = Direction.E;
        for (int i = 0; i < 9; i++) {
            dir = dir.next();
        }
        Assert.assertEquals(Direction.SE, dir);
    }

    @Test
    public void testNextSteps() {
        Direction dir = Direction.E;
        for (int i = 0; i < 9; i++) {
            dir = dir.next(2);
        }
        Assert.assertEquals(Direction.S, dir);
    }

}
