import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String): List<String>? {
    val path = Path("src/$name.txt")

    return if (path.exists()) {
        path.readLines()
    } else {
        null
    }
}

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println(day: Int, part: Int) = println("Day ${
    day.toString().padStart(2, '0')
}, Part ${part}: $this")

fun <T> T?.ifNull(cb: () -> T?) = this ?: cb()
