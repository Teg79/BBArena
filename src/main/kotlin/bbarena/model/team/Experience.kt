package bbarena.model.team

import bbarena.model.util.Concat

import java.io.Serializable

data class Experience(var comp: Int = 0, var td: Int = 0, var inter: Int = 0, var cas: Int = 0, var mvp: Int = 0, var old: Int = 0, var scars: Int = 0, var fouls: Int = 0, var rush: Int = 0) : Serializable {

    val spp: Int
        get() = td * 3 + comp + inter * 2 + cas * 2 + mvp * 5

    fun add(exp: Experience) {
        cas += exp.cas
        comp += exp.comp
        fouls += exp.fouls
        inter += exp.inter
        mvp += exp.mvp
        old += exp.old
        rush += exp.rush
        scars += exp.scars
        td += exp.td
    }

    fun sub(exp: Experience) {
        cas -= exp.cas
        comp -= exp.comp
        fouls -= exp.fouls
        inter -= exp.inter
        mvp -= exp.mvp
        old -= exp.old
        rush -= exp.rush
        scars -= exp.scars
        td -= exp.td
    }

    override fun toString(): String {
        return Concat.buildLog(javaClass,
                Pair("cas", cas),
                Pair("comp", comp),
                Pair("fouls", fouls),
                Pair("inter", inter),
                Pair("mvp", mvp),
                Pair("old", old),
                Pair("rush", rush),
                Pair("scars", scars),
                Pair("td", td))
    }

}
