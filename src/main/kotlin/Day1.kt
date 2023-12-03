import java.io.File

private val numberRegex = Regex("""(\d|one|two|three|four|five|six|seven|eight|nine)""")
private val numberReverseRegex = Regex("""(\d|enin|thgie|neves|xis|evif|ruof|eerht|owt|eno)""")

private val validNumbers = mapOf(
    "1" to 1,
    "2" to 2,
    "3" to 3,
    "4" to 4,
    "5" to 5,
    "6" to 6,
    "7" to 7,
    "8" to 8,
    "9" to 9,
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

fun sumOfCalibrationValues(input: List<String>): Int {
    return input.sumOf {
        "${it.first { it.isDigit() }}${it.last { it.isDigit() }}".toInt()
    }
}

fun readFile(fileName: String): List<String> {
    val resourceFile: File = File("src/main/resources/$fileName") // Adjust the path accordingly
    return resourceFile.bufferedReader().use { it.readLines() }
}

fun sumOfCalibrationValuesWithStringDigits(input: List<String>): Int {
    return input.mapNotNull { line ->
        validNumbers[numberRegex.find(line)?.value ?: ""]
            ?.let { firstDigit ->
                validNumbers[numberReverseRegex.find(line.reversed())?.value?.reversed() ?: ""]
                    ?.let { secondDigit ->
                        "$firstDigit$secondDigit".toInt()
                    }
            }
    }.sum()
}

fun main(args: Array<String>) {
    println("Advent 2023 - Day1")
    // Part 1
//    val input = readFile("day-1-input1.txt")
//    val sumOfCalibrationValues = sumOfCalibrationValues(input)
//    println("Sum: $sumOfCalibrationValues")

    // Part 2
    val input = readFile("day-1-input2.txt")
    val sumOfCalibrationValues = sumOfCalibrationValuesWithStringDigits(input)

    println("Sum: $sumOfCalibrationValues")
}


