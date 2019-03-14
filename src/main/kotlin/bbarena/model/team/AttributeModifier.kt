package bbarena.model.team

import java.io.Serializable

fun buildAttributeModifier(modAttribute: String): AttributeModifier {
    var s = modAttribute.trim()
    if (s.startsWith("+")) {
        s = s.substring(1)
    }

    var endModId = 1
    while (s[endModId] in '0'..'9') {
        endModId++
    }
    return AttributeModifier(Integer.parseInt(s.substring(0, endModId).trim()), parseAttribute(s.substring(endModId).trim()))
}

fun buildAttributeModifier(mod: Int, attribute: String) = AttributeModifier(mod, parseAttribute(attribute))

data class AttributeModifier(val mod: Int, val attribute: Attribute) : Serializable {

    override fun toString(): String {
        return "$mod $attribute"
    }

}
