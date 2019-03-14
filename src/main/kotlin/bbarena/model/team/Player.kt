package bbarena.model.team

import bbarena.model.Choice
import bbarena.model.pitch.Ball
import bbarena.model.pitch.Square
import java.io.Serializable
import java.util.*

data class Player(val id: Long, val num: Int, val name: String, var team: Team?, val template: PlayerTemplate,
                  val experience: Experience = Experience(), val skills: MutableList<Skill> = mutableListOf(),
                  val attributesModifiers: MutableList<AttributeModifier> = mutableListOf()) : Choice, Serializable {

    val newExperience: Experience = Experience()
    var injury = ""
    var isMng = false
    var nigglings = 0
    var type = PlayerType.NORMAL
    var pitchStatus: PlayerPitchStatus = PlayerPitchStatus.STANDING

    private var tz = true
    private var moved = false

    var ball: Ball? = null
    var square: Square? = null
    var isInDugout = true

    /**
     * A player can be used in the Kick-Off set up only if this is true
     */
    var isPlayable = true

    val allSkills: String
        get() {
            val skills = StringBuffer()
            if (skills.isNotEmpty()) {
                skills.append(skillsString)
            }
            if (attributesModifiers.isNotEmpty()) {
                if (skills.isNotEmpty()) {
                    skills.append(", ")
                }
                skills.append(attributesString)
            }

            return skills.toString()
        }

    /**
     * Getter for property position.
     *
     * @return Value of property position.
     */
    val position: String
        get() = template.position

    /**
     * Getter for property ma.
     *
     * @return Value of property ma.
     */
    val ma: Int
        get() = template.attributes.getAttribute(Attribute.MA) + getAttributeModifier(Attribute.MA)

    /**
     * Getter for property st.
     *
     * @return Value of property st.
     */
    val st: Int
        get() = template.attributes.getAttribute(Attribute.ST) + getAttributeModifier(Attribute.ST)

    /**
     * Getter for property ag.
     *
     * @return Value of property ag.
     */
    val ag: Int
        get() = template.attributes.getAttribute(Attribute.AG) + getAttributeModifier(Attribute.AG)

    /**
     * Getter for property av.
     *
     * @return Value of property av.
     */
    val av: Int
        get() = template.attributes.getAttribute(Attribute.AV) + getAttributeModifier(Attribute.AV)

    var skillsString: String
        get() {
            val sb = StringBuffer()
            for (i in skills.indices) {
                if (i > 0) {
                    sb.append(", ")
                }
                sb.append(skills[i].name)
            }
            return sb.toString()
        }
        set(newSkills) {
            val stringSkills = tokenizeString(newSkills)
            val listskills = ArrayList<Skill>()
            for (skill in stringSkills) {
                listskills.add(getSkill(skill))
            }
            skills.clear()
            skills.addAll(listskills)
        }

    var attributesString: String
        get() = attributesModifiers.joinToString()
        set(attributes) {
            attributesModifiers.clear()
            tokenizeString(attributes).forEach {
                attributesModifiers.add(buildAttributeModifier(it))
            }
        }

    val race: String
        get() = template.roster.race

    val cost: Long
        get() = template.cost

    val value: Long
        get() = if (isMng) 0L else {
            var res = 0L
            res += cost
            res += (50000 * getAttributeModifier(Attribute.ST))
            res += (40000 * getAttributeModifier(Attribute.AG))
            res += (30000 * getAttributeModifier(Attribute.AV))
            res += (30000 * getAttributeModifier(Attribute.MA))
            for (skill in skills) {
                if (template.isNormal(skill)) {
                    res += 20000
                } else if (template.isDouble(skill)) {
                    res += 30000
                }
            }
            res
        }

    val isOnThePitch: Boolean
        get() = square != null

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        val p = other as Player
        return (team == p.team && num == p.num
                && this.template === p.template)
    }

    private fun getAttributeModifier(attribute: Attribute): Int {
        var res = 0
        for (i in attributesModifiers.indices) {
            val a = attributesModifiers[i]
            if (a.attribute == attribute) {
                res += a.mod
            }
        }
        return res
    }

    fun addSkill(skill: Skill) {
        this.skills.add(skill)
    }

    private fun tokenizeString(s: String): List<String> {
        return s.split(',')//.map { it.trim() }.filter { !it.isEmpty() }
    }

    fun addAttributeModifier(attribute: AttributeModifier) {
        this.attributesModifiers.add(attribute)
    }

    /**
     * Check if the player's Tackle Zones are active
     *
     * @return true if TZ are active
     */
    fun hasTZ(): Boolean {
        return tz
    }

    /**
     * Turns on or off the player's Tackle Zones
     *
     * @param tz
     * true to activate, false to deactivate
     */
    fun activateTZ(tz: Boolean) {
        this.tz = tz
    }

    fun hasMoved(): Boolean {
        return moved
    }

    fun setMoved(moved: Boolean) {
        this.moved = moved
    }

    fun hasBall(): Boolean {
        return this.ball != null
    }

    fun removeBall() {
        this.ball = null
    }

    override fun toString(): String {
        return "$num $name"
    }

    override fun hashCode(): Int {
        var result = num
        result = 31 * result + team.hashCode()
        result = 31 * result + template.hashCode()
        return result
    }

}
