package bbarena.model.team

fun toString(categories: Collection<SkillCategory>): String {
    val res = StringBuffer("")
    for (sc in categories) {
        res.append(sc.id)
    }

    return res.toString().toUpperCase()
}

data class SkillCategory(val id: Char, val name: String, val description: String) {

    val skills = mutableSetOf<Skill>()

    fun addSkill(skill: Skill) = this.skills.add(skill)

    override fun toString(): String {
        return name
    }

    fun hasSkill(skill: Skill): Boolean {
        return skills.contains(skill)
    }

}
