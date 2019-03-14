package bbarena.model.dice

import org.slf4j.LoggerFactory
import java.io.IOException
import java.io.InputStreamReader
import java.io.StreamTokenizer
import java.net.HttpURLConnection
import java.net.URL
import java.security.SecureRandom

/**
 *
 * @author cammerucci
 */
object RandomOrgRandomizer : SecureRandom(), DieRandomizer {

    private val log = LoggerFactory.getLogger(RandomOrgRandomizer::class.java)
    private var cacheSize = 0
    private var cache: ByteArray? = null
    private var iterator = 0

    init {
        setCache(256)
    }

    // -------- Get dice face from random.org without an ID -----------

    @Synchronized
    fun setCache(cacheSize: Int) {
        if (cacheSize < 1 || cacheSize > 16384)
            throw IllegalArgumentException(
                    "cache size must be between 1 and 16384")

        this.cacheSize = cacheSize
        this.cache = ByteArray(cacheSize)
        this.iterator = cacheSize
    }

    @Synchronized
    override fun nextBytes(bytes: ByteArray) {
        try {
            for (i in bytes.indices) {
                if (iterator == cacheSize) {
                    connectRandomOrgAndFillCache()
                    iterator = 0
                }
                bytes[i] = cache!![iterator++]
            }
        } catch (e: IOException) {
            log.warn("Exception while receiving true random bytes: "
                    + e.message + ". Use super class java.security.SecureRandom.", e)
            super.nextBytes(bytes)
        }

    }

    @Throws(IOException::class)
    private fun connectRandomOrgAndFillCache() {

        log.info("Connecting www.random.org ...")
        val randomOrg = URL("http://www.random.org/cgi-bin/randbyte?"
                + "nbytes=" + cacheSize + "&format=dec")
        val con = randomOrg.openConnection() as HttpURLConnection

        con.doInput = true
        con.doOutput = false
        con.requestMethod = "GET"

        con.setRequestProperty("User-Agent", "dice@bbarena.com")

        val st = StreamTokenizer(InputStreamReader(con
                .inputStream))

        var i = 0
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            if (st.ttype != StreamTokenizer.TT_NUMBER)
                throw IOException("received data contains non-numbers")
            cache!![i++] = st.nval.toByte()
        }

        con.disconnect()
    }

    fun getRandomDiceNumber(facesNumber: Int): Int {
        val face = nextInt(facesNumber)

        return face
    }

    // -------- Get dice face from random.org/integers with an ID -----------

    fun getRandomDiceNumberWithID(facesNumber: Int, rollId: String): Int {
        var face = 1
        try {
            face = getFaceFromRandomOrgWithID(facesNumber, rollId)
        } catch (e: IOException) {
            face = (Math.random() * facesNumber).toInt() + 1
        }

        return face
    }

    @Throws(IOException::class)
    private fun getFaceFromRandomOrgWithID(facesNumber: Int, rollId: String): Int {
        var face = 0

        log.debug("Connecting www.random.org/integers ...")
        val randomOrg = URL(
                "http://preview.random.org/integers/?num=1&min=1&max="
                        + facesNumber + "&unique=off&format=plain&rnd=id."
                        + rollId)
        val con = randomOrg.openConnection() as HttpURLConnection

        con.doInput = true
        con.doOutput = false
        con.requestMethod = "GET"

        val st = StreamTokenizer(InputStreamReader(con
                .inputStream))

        if (st.nextToken() != StreamTokenizer.TT_EOF) {
            if (st.ttype != StreamTokenizer.TT_NUMBER)
                throw IOException("received data contains non-numbers")
            else {
                face = st.nval.toInt()
            }
        }

        con.disconnect()

        return face
    }

    override fun getRollFace(faces: Int, rollId: String): Int {
        return getRandomDiceNumberWithID(faces, rollId)
        //		return nextInt(faces) + 1;
    }

}
