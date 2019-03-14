package bbarena.model.team

import java.io.Serializable
import java.util.*

/**
 * Every Skill is a singleton and must be obtained with the method
 * getSkill(name)
 *
 */

private val skills = HashMap<String, Skill>()

fun getSkill(skillName: String): Skill = skills[skillName] ?: ({
    val newSkill = Skill(skillName)
    skills[skillName] = newSkill
    newSkill
})()

data class Skill internal constructor(val name: String) : Serializable, Comparable<Skill> {

    override fun compareTo(other: Skill): Int {
        return this.name.compareTo(other.name)
    }

    companion object {

        private const val serialVersionUID = 3580068957083370543L


    }

}
