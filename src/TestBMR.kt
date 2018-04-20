import java.io.BufferedReader
import java.io.FileReader
import kotlin.math.exp
import kotlin.math.pow

fun readFromFile(readFrom : String) : String{
    val br = BufferedReader(FileReader(readFrom))
    br.use {
        val sb = StringBuilder()
        val line = it.readLine()
        sb.append(line)
        return sb.toString()
    }
}


class CoolMatrix(index: Int) {

    private var size : Int
    private var A : Array<Array<Int?>>

    init{
        size = TestBMR.M
        A = Array(size) { arrayOfNulls<Int>(size) }
        for (i in 0 until size)
            for (j in 0 until size)
                 A[i][j] = TestBMR.binary[i * size + j + index].toInt() - 48
    }

    constructor(size : Int, value : Array<Array<Int?>>) : this(index = 0){
        this.size = size
        this.A = Array(size) { arrayOfNulls<Int>(size) }
        for (i in 0 until size)
            for (j in 0 until size)
                A[i][j] = value[i][j]
    }

    private fun xorI(a: Int, b: Int): Int {
        var bufA = false
        var bufB = false
        if (a == 1) bufA = true
        if (b == 1) bufB = true
        return if (bufA.xor(bufB)) 1
        else 0
    }

    private fun xorRows(first : Int, second : Int){
        for (i in 0 until size)
            A[second][i] = xorI(A[first][i]!!, A[second][i]!!)
    }

    private fun switchRows(first: Int, second: Int){
        for (i in 0 until size) {
            A[first][i] = A[second][i].also { A[second][i] = A[first][i] }
        }
    }

    fun getRank() : Int{
        var rank = 0
        var isEdited: Boolean
        var i = 0
        //printMatrix()
        while (i in 0 until size-1){
            isEdited = false
            when (A[i][i]){
                1 -> {
                    for (j in i+1 until size)
                        if (A[j][i] == 1)
                            xorRows(i, j)
                }
                0 -> {
                    for (j in i+1 until size)
                        if (A[j][i] == 1){
                            switchRows(i,j)
                            isEdited = true
                            break
                        }
                }
            }
            if (!isEdited) i++
            //printMatrix()
        }

        while (i in size-1 downTo 1){
            isEdited = false
            when(A[i][i]){
                1 -> {
                    for (j in i-1 downTo 0)
                        if (A[j][i] == 1)
                            xorRows(i,j)
                }
                0 -> {
                    for (j in i-1 downTo 0)
                        if (A[j][i] == 1){
                            switchRows(i,j)
                            isEdited = true
                            break
                        }
                }
            }
            if (!isEdited) i--
            //printMatrix()
        }

        for (k in 0 until size)
            for (j in 0 until size){
            if (A[k][j] == 1){
                rank++
                break
            }
            }
        return rank
    }

    fun printMatrix(){
        for (i in 0 until size)
            println(A[i].toList())
        println()
    }
}


class TestBMR {

    companion object Settings {
        const val M = 32
        const val Q = 32
        var c = readFromFile(OUTPUT_BINARY_FORM).length
        var N = c / M / Q
        val binary = readFromFile(OUTPUT_BINARY_FORM)
        /*val test = Array(6){ arrayOfNulls<Int>(6) }
        test[0] = arrayOf(1,0,0,0,0,0)
        test[1] = arrayOf(0,0,0,0,0,1)
        test[2] = arrayOf(1,0,0,0,0,1)
        test[3] = arrayOf(1,0,1,0,1,0)
        test[4] = arrayOf(0,0,1,0,1,1)
        test[5] = arrayOf(0,0,0,0,1,0)*/

    }

    init {

        var fMax = 0
        var fMaxMinusOne = 0
        var remain = 0
        for(i in 0 until N) {
            //val mat = CoolMatrix(6, test)
            val mat = CoolMatrix(i)

            val rank = mat.getRank()
            when (rank) {
                M -> fMax++
                M - 1 -> fMaxMinusOne++
                else -> remain++
            }
        }

        val magic = (fMax - 0.2888 * N).pow(2)/(0.2888 * N) + (fMaxMinusOne - 0.5776 * N).pow(2)/(0.5776 * N) + (remain - 0.1336 * N).pow(2)/(0.1336 * N)
        val pValue = exp(-magic / 2)

        println("\n--------------------------------\n" +
                "    BINARY MATRIX RANK TEST\n" +
                "--------------------------------\n" +
                "Note: ${c - N*M*Q} bits will be discarded\n" +
                "--------------------------------\n" +
                "c: $c\n" +
                "N: $N\n" +
                "--------------------------------\n" +
                "fMax: $fMax\n" +
                "fMaxMinusOne: $fMaxMinusOne\n" +
                "remain: $remain\n" +
                "--------------------------------\n" +
                "magic: $magic\n" +
                "pValue: $pValue\n" +
                "--------------------------------")

        when {
            pValue <= 0.01 -> println("The value is NOT Random!\n" +
                    "--------------------------------")
            else -> println("The value is Random!\n" +
            "--------------------------------\n" +
            "################################")
        }

    }
}