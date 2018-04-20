import java.io.File

class GeneratorMWC(x0 : Int, c0 : Int, a : Int, N : Int, count : Int) {

    private val result = mutableListOf<Int>()
    private var resultBinary = String()
    private var x = x0
    private var c = c0

    /**---MAX PERIOD = a*N-1 ---*/

    init{
        var isPrinted = false
        for (kek in 0 until count){
            result += (a * x + c) % N
            c = (a * x + c) / N
            x = result[kek]
            if (x == x0 && c == c0 && !isPrinted){
                    println("--------------------------------\n" +
                            "PERIOD : $kek")
                    isPrinted = true
                }
        }
        println("MAX PERIOD = a*N-2 = ${a*N-1}")
        resultBinary = toBinary()
        File(OUTPUT_DECIMAL_FORM).bufferedWriter().use { out -> out.write(result.toString()) }
        println("Wrote Decimal to decimal.txt")
        File(OUTPUT_BINARY_FORM).bufferedWriter().use { out -> out.write(resultBinary) }
        println("Wrote Binary to binary.txt")
    }

    private fun String.extend(): String {
        var bin = this
        while (bin.length < 5) bin = StringBuilder(bin).insert(0, '0').toString()
        return bin
    }

    private fun toBinary() : String {
        val binary = StringBuffer()
        for (i in 0 until  result.lastIndex)
            binary.append(Integer.toBinaryString(result[i]))
        println("LENGTH : ${binary.length}\n" +
                "--------------------------------")
        return binary.toString()
    }





}