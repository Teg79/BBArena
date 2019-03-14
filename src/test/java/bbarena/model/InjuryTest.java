package bbarena.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class InjuryTest {

    @Test
    public void testInjury() {
        List<Injury> injs = Injury.Companion.parseInjury("N+MNG Crowd; AC");

        Injury injury1 = injs.get(0);
        Assert.assertEquals(Injury.InjuryType.N + Injury.Companion.getINJ_TYPE_SEPARATOR() + Injury.InjuryType.MNG, injury1.getType());
        Assert.assertEquals(Injury.InjuryCause.Crowd.toString(), injury1.getCause());

        Injury injury2 = injs.get(1);
        Assert.assertEquals(Injury.InjuryType.AC.toString(), injury2.getType());
    }

}
