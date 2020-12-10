package `manejo-menu`

import Estudiante
import Materia
import `manejo-archivo`.escribirEnArchivo
import `manejo-archivo`.leerArchivo
import buscarEstudiante
import crearEstudiante
import editarEstudiante
import eliminarEstudiante
import java.time.LocalDateTime
import javax.swing.JOptionPane


fun stringMenuInicioEstudiante(): String {
    return "      GESTION DE ESTUDIANTES      \n\n" +
            "Seleccione las opciones:\n" +
            "1. Crear estudiante\n" +
            "2. Actualizar estudiante\n" +
            "3. Eliminar estudiante\n" +
            "4. Buscar estudiante por atributo\n" +
            "5. Volver a gestion materias\n" +
            "6. Listar todo\n" +
            "0. Volver a menu materias\n"
}

fun crearEstudianteMenu() {
    val datos = leerArchivo()
    val nombreMateria = JOptionPane.showInputDialog("Ingrese el nombre de la materia donde crear")
    val nombre = JOptionPane.showInputDialog("Ingrese el nombre")
    val codigo = JOptionPane.showInputDialog("Ingrese el codigo unico")
    val sexo = sexoEstudiante()
    val tieneBeca = estadoBeca()
    val fechaRegistro = LocalDateTime.now()
    val estudiante = Estudiante(nombre, codigo, sexo, fechaRegistro, tieneBeca)
    val estudianteCreado = crearEstudiante(nombreMateria, estudiante, datos)
    escribirEnArchivo(estudianteCreado)
}

fun editarEstudianteMenu() {
    val datos = leerArchivo()
    val nombre = JOptionPane.showInputDialog("Ingrese el codigo de estudiante a editar")
    val campo = JOptionPane.showInputDialog("Ingrese el campo de estudiante a editar")
    val nuevoValor = JOptionPane.showInputDialog("Ingrese el nuevo de estudiante a editar")
    val respuesta = editarEstudiante(nombre, campo, nuevoValor, datos)
    escribirEnArchivo(respuesta)
}

fun eliminarEstudianteMenu() {
    val datos = leerArchivo()
    val nombre = JOptionPane.showInputDialog("Ingrese el codigo de estudiante a eliminar")
    val respuesta = eliminarEstudiante(nombre, datos)
    escribirEnArchivo(respuesta)
}

fun buscarEstudiantePorAtributoMenu() {
    val datos = leerArchivo()
    val campo = JOptionPane.showInputDialog("Ingrese el campo por el que desea buscar")
    val consulta = JOptionPane.showInputDialog("Ingrese su busqueda")
    val respuesta = buscarEstudiante(campo, consulta, datos)
    var stringRespuesta = ""
    respuesta.forEach { list ->
        val materia: Materia = list?.get(0) as Materia
        val arregloEstudiantes: List<Estudiante> = list?.get(1) as List<Estudiante>
        val nombresEstudiantes = arregloEstudiantes.map { estudiante: Estudiante ->
            val arregloDatos = mutableMapOf<String, Any>();
            arregloDatos.put("Nombre", estudiante.nombre)
            arregloDatos.put("Codigo", estudiante.codigo)
            arregloDatos.put("Sexo", estudiante.sexo)
            arregloDatos.put("Fecha registro", estudiante.fechaRegistro)
            arregloDatos.put("Tiene beca", estudiante.tieneBeca)
            return@map arregloDatos
        }
        stringRespuesta += "-----------------------------------------------------------------------------------------\n" +
                "Materia: ${materia.nombre}\n" +
                "Estudiantes coinciden: ${nombresEstudiantes}\n"
    }
    stringRespuesta += "-----------------------------------------------------------------------------------------\n"
    JOptionPane.showMessageDialog(null, stringRespuesta)
}

fun sexoEstudiante(): Char {
    val opciones = arrayOf("Masculino", "Femenino")
    val respuesta = JOptionPane.showOptionDialog(null, "Seleccione el sexo de estudiante",
            "Sexo",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0])
    if (respuesta == 0) {
        return "M".single()
    } else {
        return "F".single()
    }
}

fun estadoBeca(): Boolean {
    val opciones = arrayOf("Si", "No")
    val respuesta = JOptionPane.showOptionDialog(null, "El alumno tiene beca",
            "Beca",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0])
    return respuesta == 0
}
