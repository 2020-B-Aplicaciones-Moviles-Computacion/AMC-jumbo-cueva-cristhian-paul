import java.io.File
import java.io.InputStream
import javax.swing.JOptionPane

class Materia(
    var nombre: String,
    var creditos: Int,
    var codigo: String,
    var habilitado: Boolean = true,
    var estudiantes: MutableList<Estudiante>? = null
) {
}

fun buscarMateria(
    campo: String,
    consulta: String,
    materiades: MutableList<Materia>
): List<Materia> {
    var materiadesEncontradas: List<Materia> = emptyList()
    when (campo) {
        "nombre" -> {
            materiadesEncontradas = materiades
                .filter { materia: Materia ->
                    return@filter materia.nombre == consulta
                }
        }
        "creditos" -> {
            val consultaInt = consulta.toInt()
            materiadesEncontradas = materiades
                .filter { materia: Materia ->
                    return@filter materia.creditos == consultaInt
                }
        }
        "codigo" -> {
            materiadesEncontradas = materiades
                .filter { materia: Materia ->
                    return@filter materia.codigo == consulta
                }
        }
        "habilitado" -> {
            val consultaBool = consulta.toBoolean()
            materiadesEncontradas = materiades
                .filter { materia: Materia ->
                    return@filter materia.habilitado == consultaBool
                }
        }
        else -> {
            JOptionPane.showMessageDialog(null,"Campo ${campo} no encontrado")
//            println("Campo ${campo} no encontrado")
        }
    }
    return materiadesEncontradas
}

fun editarMateria(
    nombre: String,
    campoAEditar: String,
    nuevoValor: String,
    materiades: MutableList<Materia>
): MutableList<Materia> {
    val indice = buscarYRetornarIndice(nombre, materiades)
    val existeMateria = indice > -1
    if (existeMateria) {
        when (campoAEditar) {
            "nombre" -> {
                materiades[indice].nombre = nuevoValor
            }
            "creditos" -> {
                materiades[indice].creditos = nuevoValor.toInt()
            }
            "codigo" -> {
                materiades[indice].codigo = nuevoValor
            }
            "habilitado" -> {
                materiades[indice].habilitado = nuevoValor.toBoolean()
            }
            else -> {
                JOptionPane.showMessageDialog(null, "No se encontro campo ${campoAEditar}")
            }
        }
    }
    return materiades
}

fun crearMateria(
    nombre: String,
    creditos: Int,
    codigo: String
): MutableList<Materia> {
    val estudiantes = mutableListOf<Estudiante>()
    return mutableListOf(Materia(nombre, creditos, codigo, estudiantes = estudiantes))
}

fun eliminarMateria(
    nombre: String,
    materiades: MutableList<Materia>
): MutableList<Materia> {
    val indice = buscarYRetornarIndice(nombre, materiades)
    val existeMateria = indice > -1
    if (existeMateria) {
        materiades.removeAt(indice)
    }
    return materiades
}

fun buscarYRetornarIndice(nombre: String, materiades: MutableList<Materia>): Int {
    val respuesta = materiades.filter { materia: Materia ->
        return@filter materia.nombre == nombre
    }
    val existeMateria = respuesta.size > 0
    if (!existeMateria) {
        JOptionPane.showMessageDialog(null, "No se encontro materiad ${nombre}")
//        println("No se encontro materiad ${nombre}")
        return -1
    }
    return materiades.indexOf(respuesta[0])

}

fun listarTodo(): String {
    val inputStream: InputStream = File("db.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    return inputString
}
