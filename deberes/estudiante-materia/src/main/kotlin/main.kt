import `manejo-menu`.*
import javax.swing.JOptionPane

fun main(args: Array<String>) {
    menu()
}


fun menu() {
    val menuInicio = stringMenuInicio()
    do {
        val entradaTexto = JOptionPane.showInputDialog(menuInicio)
        val opcion = Integer.parseInt(entradaTexto)
        when (opcion) {
            1 -> {
                crearMateriaMenu()
            }
            2 -> {
                editarMateriaMenu()
            }
            3 -> {
                eliminarMateriaMenu()
            }
            4 -> {
                buscarMateriaMenu()
            }
            5 -> {
                listarTodoMenu()
            }
            6 -> {
                menuEstudiantil()
            }
            else -> {
                JOptionPane.showMessageDialog(null, "Opcion seleccionada no existe")
//                println("Opcion seleccionada no existe")
            }
        }
    } while (opcion != 0)
}

fun menuEstudiantil() {
    val menuInicioEstudiante = stringMenuInicioEstudiante()
    do {
        val entradaTexto = JOptionPane.showInputDialog(menuInicioEstudiante)
        val opcion = Integer.parseInt(entradaTexto)
        when (opcion) {
            1 -> {
                crearEstudianteMenu()
            }
            2 -> {
                editarEstudianteMenu()
            }
            3 -> {
                eliminarEstudianteMenu()
            }
            4 -> {
                buscarEstudiantePorAtributoMenu()
            }
            5 -> {
                menu()
            }
            6 -> {
                listarTodoMenu()
            }
            else -> {
                JOptionPane.showMessageDialog(null, "Opcion seleccionada no existe")
//                println("Opcion seleccionada no existe")
            }
        }
    } while (opcion == 0)
}
