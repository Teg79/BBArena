package bbarena.model;

import bbarena.model.team.AttributeModifier;
import bbarena.model.team.Attributes;
import org.junit.Assert;
import org.junit.Test;

public class TestAttributeModifier {

    @Test
    public void testAttributeModifier() {
        AttributeModifier b = new AttributeModifier("-10 St");
        Assert.assertEquals(-10, b.getMod());
        Assert.assertEquals(Attributes.Attribute.ST, b.getType());

        AttributeModifier a = new AttributeModifier("+13Ag");
        Assert.assertEquals(13, a.getMod());
        Assert.assertEquals(Attributes.Attribute.AG, a.getType());
    }
}
