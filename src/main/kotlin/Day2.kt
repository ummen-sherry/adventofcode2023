import java.util.Locale
import java.util.Optional
import kotlin.jvm.optionals.getOrDefault

data class GameLimit(val redLimit: Int = 12, val greenLimit: Int = 13, val blueLimit: Int = 14)

fun GameLimit.isValid(color: String, count: Int): Boolean {
    return when (color.lowercase(Locale.getDefault())) {
        "red" -> this.redLimit >= count
        "green" -> this.greenLimit >= count
        "blue" -> this.blueLimit >= count
        else -> false
    }
}

private val gameLimit = GameLimit()

fun sumOfIDOfLegitGames(input: List<String>): Int {
    return input.sumOf {
        val limitExceededGameId = getLimitExceededGameId(it).getOrDefault(0)
        limitExceededGameId
    }
}

fun getLimitExceededGameId(gameInput: String): Optional<Int> {
    val regex = Regex("Game (\\d+): (.*)")
    val matchResult = regex.find(gameInput)
    val groups = matchResult?.groupValues!!
    val didLimitExceed = didLimitExceed(groups[1].toInt(), groups[2])
    return if (!didLimitExceed) Optional.of(groups[1].toInt()) else Optional.empty<Int>()
}

fun didLimitExceed(gameId: Int, gameColorInput: String): Boolean {
    val numberRegex = Regex("""(\d+) (\w+)""")
    val numberMatches = numberRegex.findAll(gameColorInput)

    numberMatches.forEach { matchResult ->
        val groups = matchResult.groupValues
        val count = groups[1].toInt()
        val color = groups[2]

        if (!gameLimit.isValid(color = color, count)) return true
    }
    return false
}

fun sumOfIDAndPowerOfLegitGames(input: List<String>): Int {
    return input.sumOf {
        val (gameId, power) = getMinPowerAndGameId(it)
        if (gameId != 0) {
            println("Game $gameId: Minimum Power = $power")
        }
        power
    }
}

fun getMinPowerAndGameId(gameInput: String): Pair<Int, Int> {
    val regex = Regex("Game (\\d+): (.*)")
    val matchResult = regex.find(gameInput)
    val groups = matchResult?.groupValues!!

    val gameId = groups[1].toInt()
    val minPower = calculateMinPower(groups[2])

    return Pair(gameId, minPower)
}

fun calculateMinPower(gameColorInput: String): Int {
    val numberRegex = Regex("""(\d+) (\w+)""")
    val numberMatches = numberRegex.findAll(gameColorInput)

    var maxRed = 1
    var maxGreen = 1
    var maxBlue = 1

    for (matchResult in numberMatches) {
        val groups = matchResult.groupValues
        val count = groups[1].toInt()
        val color = groups[2]

        when (color) {
            "red" -> maxRed = maxOf(maxRed, count)
            "green" -> maxGreen = maxOf(maxGreen, count)
            "blue" -> maxBlue = maxOf(maxBlue, count)
        }
    }

    return maxRed * maxGreen * maxBlue
}


fun main(args: Array<String>) {
    println("Advent 2023 - Day2")


    val input = readFile("day-2-input1.txt")
    // Part 1
//    val sumOfIDOfLegitGames = sumOfIDOfLegitGames(input)
//
//    println("Sum: $sumOfIDOfLegitGames")


    // Part2
    val sumOfIDAndPowerOfLegitGames = sumOfIDAndPowerOfLegitGames(input)

    println("Sum: $sumOfIDAndPowerOfLegitGames")
}