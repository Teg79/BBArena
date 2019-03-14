package bbarena.model.team

import java.io.Serializable

enum class Attribute {
    MA, ST, AG, AV;
}

fun parseAttribute(attribute: String): Attribute {
    val a = attribute.trim { it <= ' ' }.substring(0, 2).toUpperCase()
    return when (a) {
        Attribute.MA.toString() -> Attribute.MA
        Attribute.ST.toString() -> Attribute.ST
        Attribute.AG.toString() -> Attribute.AG
        Attribute.AV.toString() -> Attribute.AV
        else -> throw RuntimeException("Unsupported Attribute $attribute")
    }
}

class Attributes() : Serializable {

    private val attributeMap = mutableMapOf<Attribute, Int>()

    constructor(ma: Int, st: Int, ag: Int, av: Int) : this() {
        attributeMap[Attribute.MA] = ma
        attributeMap[Attribute.ST] = st
        attributeMap[Attribute.AG] = ag
        attributeMap[Attribute.AV] = av
    }

    fun getAttribute(attribute: Attribute): Int {
        return attributeMap[attribute] ?: throw RuntimeException("Attribute $attribute not present, valid values are ${attributeMap.keys}")
    }

}
