import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.swing.JOptionPane

class Estudiante(
        var nombre: String,
        var codigo: String,
        var sexo: Char,
        var fechaRegistro: LocalDateTime,
        var tieneBeca: Boolean = false
) {
    enum class ESTADO_CIVIL {
        soltero,
        casado,
        viudo,
        divorsiado
    }
}

fun buscarEstudiante(
        campo: String,
        consulta: String,
        materias: MutableList<Materia>
): List<List<Any>?> {
    var materiasEncontrados: List<List<Any>?> = emptyList()
    when (campo) {
        "nombre" -> {
            materiasEncontrados = materias
                    .map { universidad: Materia ->
                        val universidaConEstudiante = universidad.estudiantes?.filter { estudiante: Estudiante ->
                            return@filter estudiante.nombre == consulta
                        }
                        return@map universidaConEstudiante?.let { listOf<Any>(universidad, it) }
                    }.filter { list: List<Any>? ->
                        return@filter list != null
                    }.filter { list ->
                        val estudiantesFiltrados = list?.get(1) as List<Estudiante>
                        return@filter estudiantesFiltrados.size > 0
                    }
        }
        "codigo" -> {
            materiasEncontrados = materias
                    .map { universidad: Materia ->
                        val universidaConEstudiante = universidad.estudiantes?.filter { estudiante: Estudiante ->
                            return@filter estudiante.codigo == consulta
                        }
                        return@map universidaConEstudiante?.let { listOf<Any>(universidad, it) }
                    }.filter { list: List<Any>? ->
                        return@filter list != null
                    }.filter { list ->
                        val estudiantesFiltrados = list?.get(1) as List<Estudiante>
                        return@filter estudiantesFiltrados.size > 0
                    }
        }
        "sexo" -> {
            materiasEncontrados = materias
                    .map { universidad: Materia ->
                        val universidaConEstudiante = universidad.estudiantes?.filter { estudiante: Estudiante ->
                            return@filter estudiante.sexo == consulta.single()
                        }
                        return@map universidaConEstudiante?.let { listOf<Any>(universidad, it) }
                    }.filter { list: List<Any>? ->
                        return@filter list != null
                    }.filter { list ->
                        val estudiantesFiltrados = list?.get(1) as List<Estudiante>
                        return@filter estudiantesFiltrados.size > 0
                    }
        }
//        "fechaRegistro" -> {
//            val fecha = LocalDate.parse(consulta, DateTimeFormatter.ISO_DATE)
//            materiasEncontrados = materias
//                .map { universidad: Universida ->
//                    val universidaConEstudiante = universidad.estudiantes?.filter { estudiante: Estudiante ->
//                        return@filter estudiante.fechaRegistro == fecha
//                    }
//                    return@map universidaConEstudiante?.let { listOf<Any>(universidad, it) }
//                }.filter { list: List<Any>? ->
//                    return@filter list != null
//                }.filter { list ->
//                    val estudiantesFiltrados = list?.get(1) as List<Estudiante>
//                    return@filter estudiantesFiltrados.size > 0
//                }
//        }
        "tieneBeca" -> {
            materiasEncontrados = materias
                    .map { universidad: Materia ->
                        val universidaConEstudiante = universidad.estudiantes?.filter { estudiante: Estudiante ->
                            return@filter estudiante.tieneBeca == consulta.toBoolean()
                        }
                        return@map universidaConEstudiante?.let { listOf<Any>(universidad, it) }
                    }.filter { list: List<Any>? ->
                        return@filter list != null
                    }.filter { list ->
                        val estudiantesFiltrados = list?.get(1) as List<Estudiante>
                        return@filter estudiantesFiltrados.size > 0
                    }
        }
        else -> {
            JOptionPane.showMessageDialog(null, "Campo ${campo} no encontrado")
//            println("Campo ${campo} no encontrado")
        }
    }
    return materiasEncontrados
}

fun editarEstudiante(
        codigo: String,
        campoAEditar: String,
        nuevoValor: String,
        materias: MutableList<Materia>
): MutableList<Materia> {
    val indices = buscarYRetornarIndices(codigo, materias)
    val existeMateria = indices["universidad"]!! > -1
    if (existeMateria) {
        val indiceMateria = indices["universidad"] as Int
        val indiceEstudiante = indices["estudiante"] as Int
        when (campoAEditar) {
            "nombre" -> {
                materias[indiceMateria].estudiantes?.get(indiceEstudiante)?.nombre = nuevoValor
            }
            "sexo" -> {
                materias[indiceMateria].estudiantes?.get(indiceEstudiante)?.sexo = nuevoValor.single()
            }
            "tieneBeca" -> {
                materias[indiceMateria].estudiantes?.get(indiceEstudiante)?.tieneBeca = nuevoValor.toBoolean()
            }
        }

    }
    return materias
}


fun crearEstudiante(
        nombreMateria: String,
        estudiante: Estudiante,
        materias: MutableList<Materia>
): MutableList<Materia> {
    val indiceUniversida = buscarYRetornarIndice(nombreMateria, materias)
    val existeMateria = indiceUniversida > -1
    if (existeMateria) {
        materias[indiceUniversida].estudiantes?.add(estudiante)
    }
    return materias
}


fun eliminarEstudiante(
        codigo: String,
        materias: MutableList<Materia>
): MutableList<Materia> {
    val indices = buscarYRetornarIndices(codigo, materias)
    val existeMateria = indices["materia"]!! > -1
    val existeEstudiante = indices["estudiante"]!! > -1

    if (existeMateria && existeEstudiante) {
        val indiceMateria = indices["materia"] as Int
        val indiceEstudiante = indices["estudiante"] as Int
        materias[indiceMateria].estudiantes?.removeAt(indiceEstudiante)
    }
    return materias
}

fun buscarYRetornarIndices(
        codigo: String,
        materias: MutableList<Materia>
): Map<String, Int?> {
    val respuesta = materias
            .map { universidad: Materia ->
                val universidaConEstudiante = universidad.estudiantes?.filter { estudiante: Estudiante ->
                    return@filter estudiante.codigo == codigo
                }
                return@map universidaConEstudiante?.let { listOf<Any>(universidad, it) }
            }.filter { list: List<Any>? ->
                return@filter list != null
            }.filter { list ->
                val estudiantesFiltrados = list?.get(1) as List<Estudiante>
                return@filter estudiantesFiltrados.size > 0
            }

    val encontroRespuesta = respuesta.size > 0
    if (!encontroRespuesta) {
        JOptionPane.showMessageDialog(null, "No se encontro estudiante: ${codigo}")
//        println("No se encontro estudiante: ${codigo}")
        return mapOf<String, Int?>("materia" to -1, "estudiante" to -1)
    }
    val indiceMateria = materias.indexOf(respuesta[0]?.get(0))
    val estudiante = respuesta[0]?.get(1) as List<Estudiante>
    val indiceEstudainte = materias[indiceMateria].estudiantes?.indexOf(estudiante[0])
    val indices = mapOf<String, Int?>("materia" to indiceMateria, "estudiante" to indiceEstudainte)
    return indices
}