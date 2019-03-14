package bbarena.model.dice

import java.security.SecureRandom

/**
 *
 * @author cammerucci
 */
class JavaRandomizer : DieRandomizer {

    private val random = SecureRandom()
    private val results = mutableMapOf<String, Int>()

    fun getRollFace(facesNumber: Int): Int {
        return getRollFace(facesNumber, "")
    }

    override fun getRollFace(faces: Int, rollId: String): Int {
        return results[rollId] ?: {
            val res = random.nextInt(faces - 1) + 1
            results[rollId] = res
            res
        }.invoke()
    }
}
