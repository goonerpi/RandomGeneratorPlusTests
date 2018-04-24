import org.apache.commons.math3.special.Gamma.regularizedGammaQ

class TestLongRunOfOnes{

    companion object {
        val binary = readFromFile(INPUT)
        val n = readFromFile(INPUT).length
        const val M = 10000
        const val K = 6
        const val N = 75
    }

    private val arrayOfBlocks = Array(n / M){ arrayOfNulls<Int>(M)}
    private val categories = Array(7){0}
    private val pi = arrayOf(0.0882, 0.2092, 0.2483, 0.1933, 0.1208, 0.0675, 0.0727)
    private var ksi = 0.0
    private val pValue : Double

    init {
        val s = n/ M
        for (i in 0 until s)
            for (j in 0 until M)
                arrayOfBlocks[i][j] = binary[i * M + j].toInt() - 48

        for (i in 0 until s)
            when(findLRON(arrayOfBlocks[i])){
                in 0..10 -> categories[0] = categories[0].plus(1)
                11 -> categories[1] = categories[1].plus(1)
                12 -> categories[2] = categories[2].plus(1)
                13 -> categories[3] = categories[3].plus(1)
                14 -> categories[4] = categories[4].plus(1)
                15 -> categories[5] = categories[5].plus(1)
                else -> categories[6] = categories[6].plus(1)
            }


        for (i in 0..K)
            ksi += (categories[i] - N * pi[i]) * (categories[i] - N * pi[i]) / N / pi[i]

        pValue = regularizedGammaQ(K/2.0, ksi/2)


        println("--------------------------------\n" +
                "   LONGEST RUN OF ONES TEST\n" +
                "--------------------------------\n" +
                "n: $n\n" +
                "K: $K\n" +
                "--------------------------------\n" +
                "pi: ${pi.toList()}\n" +
                "ksi: $ksi\n" +
                "--------------------------------\n" +
                "pValue: $pValue\n" +
                "--------------------------------" )
        /*println(categories.toList())

        for (i in 0 until s)
            print("${findLRON(arrayOfBlocks[i])}|")
        println("\n--------------------------------")*/

        when {
            pValue <= 0.01 -> println("The value is NOT Random!\n" +
                    "--------------------------------")
            else -> println("The value is Random!\n" +
                    "--------------------------------")
        }

    }

    private fun findLRON(arr: Array<Int?>) : Int{
        var count = 0
        var max = 0
        for (i in 0..arr.lastIndex) {
            if (arr[i] == 1) count++
            else count = 0
            if (count > max) max = count
        }
        return max
    }
}