package bbarena.model.team

import java.util.*

class PlayerComparator @JvmOverloads constructor(compareType: PlayerComparatorType = PlayerComparatorType.COMPARE_NUM) : Comparator<Player> {

    var compareType = PlayerComparatorType.COMPARE_NUM

    enum class PlayerComparatorType {
        COMPARE_NUM, COMPARE_SPP, COMPARE_CMP, COMPARE_CAS, COMPARE_INT, COMPARE_TD, COMPARE_MVP, COMPARE_SCR
    }

    init {
        this.compareType = compareType
    }

    override fun compare(a: Player, b: Player): Int {
        var res = 0

        if (a.team != null && b.team != null) {
            res = a.team.name.compareTo(b.team.name)
        }

        if (res == 0) {
            if (compareType == PlayerComparatorType.COMPARE_SPP) {
                res = compareSpp(a, b)
            } else if (compareType == PlayerComparatorType.COMPARE_CMP) {
                res = compareCmp(a, b)
            } else if (compareType == PlayerComparatorType.COMPARE_CAS) {
                res = compareCas(a, b)
            } else if (compareType == PlayerComparatorType.COMPARE_TD) {
                res = compareTd(a, b)
            } else if (compareType == PlayerComparatorType.COMPARE_INT) {
                res = compareInt(a, b)
            } else if (compareType == PlayerComparatorType.COMPARE_MVP) {
                res = compareMvp(a, b)
            } else if (compareType == PlayerComparatorType.COMPARE_SCR) {
                res = compareScr(a, b)
            } else {
                res = compareNum(a, b)
            }
        }

        if (res == 0 && compareType == PlayerComparatorType.COMPARE_NUM) {
            res = b.name.compareTo(a.name)
        }

        return res
    }

    private fun compareNum(a: Player, b: Player) = a.num - b.num

    private fun compareSpp(a: Player, b: Player) = b.experience.spp - a.experience.spp

    private fun compareCmp(a: Player, b: Player) = b.experience.comp - a.experience.comp

    private fun compareCas(a: Player, b: Player) = b.experience.cas - a.experience.cas

    private fun compareTd(a: Player, b: Player) = b.experience.td - a.experience.td

    private fun compareInt(a: Player, b: Player) = b.experience.inter - a.experience.inter

    private fun compareMvp(a: Player, b: Player) = b.experience.mvp - a.experience.mvp

    private fun compareScr(a: Player, b: Player) = b.experience.scars - a.experience.scars

}
