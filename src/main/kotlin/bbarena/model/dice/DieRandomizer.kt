package bbarena.model.dice

/**
 *
 * @author cammerucci
 */
interface DieRandomizer {

    /**
     * Return the face number, between 1 and 'facesNumber'
     */
    fun getRollFace(faces: Int, rollId: String): Int
}
