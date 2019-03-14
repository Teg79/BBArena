package bbarena.model.team

import java.io.Serializable

data class PlayerTemplate(val roster: Roster, val quantity: Qty, val position: String, val cost: Long, val attributes: Attributes, val skills: List<Skill>, val normal: List<SkillCategory>, val doubles: List<SkillCategory>) : Serializable {

    val doublesString: String
        get() = SkillCategory.toString(doubles)

    val normalString: String
        get() = SkillCategory.toString(normal)

    val skillsString: String
        get() = skills.joinToString()

    override fun toString(): String = "$quantity $position $attributes $skillsString $normalString $doublesString"

    fun isDouble(skill: Skill): Boolean = isInCategories(doubles, skill)

    fun isNormal(skill: Skill): Boolean = isInCategories(normal, skill)

    private fun isInCategories(categories: List<SkillCategory>, skill: Skill): Boolean =
            null != categories.firstOrNull { category -> category.hasSkill(skill) }

}