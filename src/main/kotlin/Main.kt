/**
 * Application startup point representing class entry point.
 *
 * @author adavliatov
 */
class Main {

    fun run(ignored: Int) {
        println(ignored)
        if (false) println()
//        if ((null as String).startsWith("")) println()

        if (true) return

        return
    }
}

fun main(vararg args: String) {
    Main().run(2)
}
