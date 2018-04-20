import org.apache.commons.math3.special.Erf.erfc
import kotlin.math.abs
import kotlin.math.log2
import kotlin.math.sqrt


class TestMaurer{

    /** Parameters are selected for n = 1000000 */
    companion object {
        val n = readFromFile(OUTPUT_BINARY_FORM).length
        const val L = 7
        const val Q = 1280
        val K = n / L - Q
        const val expectedValue = 6.1962507
        const val variance = 3.125
        val binary = readFromFile(OUTPUT_BINARY_FORM)
    }

    private val lastIndexArray : Array<Int>
    private var decRep = 0

    init {

        lastIndexArray = Array(Math.pow(2.0, L.toDouble()).toInt()) {0}
        var sum = 0.0
        val testFunction : Double
        val c = 0.7 - 0.8 / L.toDouble() + (4 + 32 / L.toDouble()) * Math.pow(K.toDouble(), (-3 / L).toDouble()) / 15
        val sigma = c * sqrt(variance / K.toDouble())
        val pValue : Double

        for (i in 0 until Q) {
            decRep = 0
            for (j in 0 until L)
                decRep += ((binary[(i)*L + j].toInt() - 48) * Math.pow(2.0, (L - 1 - j).toDouble())).toInt()
            lastIndexArray[decRep] = i
        }

        for (i in Q until Q+K){
            decRep = 0
            for (j in 0 until L)
                decRep += ((binary[(i)*L + j].toInt() - 48) * Math.pow(2.0, (L - 1 - j).toDouble())).toInt()
            sum += log2((i - lastIndexArray[decRep]).toDouble())
            lastIndexArray[decRep] = i
        }


        testFunction = sum / (K.toDouble())
        pValue = erfc(abs((testFunction - expectedValue) / sqrt(2.0) / sigma))
        println("--------------------------------\n" +
                "  MAURER UNIVERSAL STATIC TEST\n" +
                "--------------------------------\n" +
                "n: $n\n" +
                "K: $K\n" +
                "--------------------------------\n" +
                "sum: $sum\n" +
                "testFunction: $testFunction\n" +
                "c: $c\n" +
                "sigma: $sigma\n" +
                "--------------------------------\n" +
                "pValue: $pValue\n" +
                "--------------------------------")


        when {
            pValue <= 0.01 -> println("The value is NOT Random!\n" +
                    "--------------------------------")
            else -> println("The value is Random!\n" +
                    "--------------------------------\n"+
                    "################################")
        }
    }


}