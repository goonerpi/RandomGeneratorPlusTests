const val OUTPUT_BINARY_FORM = "C:\\Users\\pavel\\IdeaProjects\\RandomGeneratorPlusTests\\src\\binary.txt"
const val OUTPUT_DECIMAL_FORM = "C:\\Users\\pavel\\IdeaProjects\\RandomGeneratorPlusTests\\src\\decimal.txt"
const val DATA_E = "C:\\Users\\pavel\\IdeaProjects\\RandomGeneratorPlusTests\\src\\data_e.txt"
const val DATA_PI = "C:\\Users\\pavel\\IdeaProjects\\RandomGeneratorPlusTests\\src\\data_pi.txt"
const val INPUT = DATA_E

fun main(args : Array<String>){

    GeneratorMWC(4, 3, 6, 30, count = 249300)
    TestBMR()
    TestMaurer()
    TestLongRunOfOnes()


}