import java.util.*
import kotlin.collections.ArrayList

fun main() {
    println("What's your name?")
    val name = readLine()
    println("Hello $name!")
    val PI = 10
    // duck typing
    // varibles mutables
    var edad: Int = 0;

    // varibles inmutables

    var cedula = 1104125883

    val nombrProfesor: String = "Adrian Eguez"
    val sueldo: Double = 12.2
    val estadoCivil: Char = 'C'

    if (sueldo == 12.2) {
        //verdadro
    } else {
        //falso
    }

    when (sueldo) {
        12.2 -> {
            println("Sueldo Normanl")
        }
        -12.2 -> {
            println("Sueldo bao")

        }
        else -> println("sueldo no reconocido")
    }

    val sueldoMayorAlEstablecido: Boolean = if (sueldo == 12.2) false else true
//    val sueldoMayorAlEstablecido: Boolean = if (sueldo == 12.2) 10 else 20

    imprimirNombre("adrian")
//    calcularSueldo(1000.00, 14.00, 3)

    calcularSueldo(
            sueldo = 1000.0,
            calcucloEspecial = 3
    )
    // no importa el orden en el que mandan


    // arreglo estatico
    val arrgloImutable: Array<Int> = arrayOf(1, 2, 3)
    // arreglo dinamico
    var arregloMutable: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)


    // Operadores arreglos
    val respuestaMueteable: Unit = arregloMutable.forEach{
        valorIteracion -> // sin definir el tipo de dato
        println("valor ${valorIteracion}")
    }

    // for each indexed

    val respuestaforEach:Unit =  arregloMutable.forEachIndexed(){
        indice, valorInteracion ->
        println("valor ${valorInteracion} ,indice: ${indice}")
    }

    val respuestaforEachMap: List<Int> = arregloMutable.map {
        valorInteracion ->
        return@map valorInteracion*10
    }
    arregloMutable.map {
        valorInteracion ->
        valorInteracion + 15
    }
    println(respuestaforEachMap)
    val respuetaFilter: List<Int> = arregloMutable.filter {
        valorInteracion ->
        val mayoreCinc: Boolean = valorInteracion > 5
        return@filter mayoreCinc
    }
    println(respuetaFilter)
    //arregloMutable.filter { i -> i > 5 }
    arregloMutable.filter { it > 5 }

    // Any All revisar un condicion dentro de un arreglo
    // OR ad AND
    // OR -> any
    // And -> all
    // OR  flase todos tienen q ser falson
    // OR True uno es verdadero todos son verdaderos

    // AND falso si uno es falso todos los demas son falson
    // AND true todos tienen que ser True

    val resouestaAny: Boolean = arregloMutable
            .any{
                valorIteracion ->
                return@any valorIteracion > 12  //TRUE  o FALSE
            }
    println(arregloMutable)
    println(resouestaAny)

    var respuestALL: Boolean = arregloMutable
            .all {
                valorIterable ->
                return@all valorIterable > 0  // TRUE o FALSE
            }
    println(arregloMutable)
    println(respuestALL)

    // REDUCE
    // 1) devuelve valor acumulado
    // 2) devuelve valor empieza
    // [1,2,3,4,5]
    // 0 = 0 - (1)
    // 1 = 1 - (2)
    //3 = 3 - (3)
    //6 = 6 - (5)

    val respuestaReduce: Int = arregloMutable
            .reduce{
                acumulado, valorIterable ->
                return@reduce acumulado + valorIterable
            }
    println(arregloMutable)
    println(respuestaReduce)

    val respuestaReduceFold: Int = arregloMutable
            .fold(
                    100,{
                        acumulado, valorIterable ->
                        return@fold acumulado - valorIterable
                    }
            )
    println(arregloMutable)
    println(respuestaReduceFold)

    //foldRight
    //reduceRight


    //OPERADORES
    //siempre devulven un valor
    // concatenar

/*    val vidaActual:Double = arregloMutable
            .map { it * 10.5 }
            .filter { it > 28 }
            .fold(
                    100.00, {
                acc: Int, i: Double -> acc - i
            })
            .also { print(it) }*/


    val ejemploUno = Suma(1,2,3)
    val ejemploDos = Suma(1, null,3)
    val ejemploTres = Suma(null, null, null)
    println(ejemploDos)
    println(Suma.historialSuma)
    println(ejemploUno)
    println(Suma.historialSuma)
    println(ejemploTres)
    println(Suma.historialSuma)

} //main cerrado

fun imprimirNombre(nombre: String) {
    println("Tu tellamas $nombre")
}

fun calcularSueldo(
        sueldo: Double, // requerido
        tase: Double = 12.00, // opcional con valor por defecto 12.00
        calcucloEspecial: Int? = null  // nulas
): Double {
    if (calcucloEspecial == null) {
        return sueldo * (100.00 / tase)
    } else {
        return sueldo * (100.00 / tase) * calcucloEspecial
    }
}
abstract class NumeroJava {
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
            uno:Int, dos: Int
    ) {
        numeroUno = uno
        numeroDos = dos
        println(numeroUno)
        println(numeroDos)


    }
}

abstract class Numeros(
        protected var numeroUno: Int,
        protected var numeroDos: Int) {
    init {
        println(numeroUno)
        println(numeroDos)
    }

}

// val numerosNumero = Numeros(1,2)
// numerosNumero.numeroUno

// Inicializar las clases super
class Suma(
        uno: Int,
        dos: Int,
        protected var tres: Int
        // cuatro: Int
): Numeros(uno, dos) {
    init {
        this.numeroDos
        this.numeroUno
        this.sumar()
        println("constructor priamrio init")
    }
    constructor(
            uno: Int, dos: Int?, tres: Int
    ): this(uno, if (dos == null) 0 else dos, tres) {

    }

    constructor(
            uno: Int?, dos: Int?, tres: Int?
    ): this(if (uno == null) 0 else uno, if (dos == null) 0 else dos, if (tres == null) 0 else tres) {

    }

    public fun sumar(): Int {
        this.tres
        val total: Int = this.numeroUno + this.numeroDos
        Suma.agregarHistorial(total)
        return total
    }

    companion object {
        val historialSuma = arrayListOf<Int>()
        fun agregarHistorial(nuevaSuma:Int) {
            this.historialSuma.add(nuevaSuma)
        }
    }


}
// como base de datos
class BaseDatos() {
    companion object{
        val datos = arrayListOf<Int>()
    }
}