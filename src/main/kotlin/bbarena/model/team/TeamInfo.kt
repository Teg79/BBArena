package bbarena.model.team

open class TeamInfo(val id: Long, val name: String, val coach: String, val race: String, val teamValue: Long) {

    override fun equals(other: Any?): Boolean =
            other is TeamInfo && this.id == other.id

    override fun hashCode(): Int =
        id.hashCode()

}
