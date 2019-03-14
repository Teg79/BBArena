package bbarena.model.util

object Concat {

    fun buildLog(type: Class<*>, vararg pairs: Pair<Any, Any>): String {
        val res = StringBuilder()
        res.append(type.simpleName)
                .append(pairs.joinToString(separator = ";", prefix = "[", postfix = "]") { it.toString() })
        return res.toString()
    }

}
