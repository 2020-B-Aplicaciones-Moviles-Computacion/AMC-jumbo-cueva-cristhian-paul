package `manejo-menu`

import Materia
import `manejo-archivo`.escribirEnArchivo
import `manejo-archivo`.leerArchivo
import buscarMateria
import crearMateria
import editarMateria
import eliminarMateria
import listarTodo
import javax.swing.JOptionPane

fun stringMenuInicio(): String {
    return "     BIENVENIDO AL SISTEMA DE UNIVERSIDADES      \n\n" +
            "Seleccione las opciones:\n" +
            "1. Crear materia\n" +
            "2. Actualizar materia\n" +
            "3. Eliminar materia\n" +
            "4. Buscar materia por atributo\n" +
            "5. Listar todas las materia\n" +
            "6. Gestionar estudiantes\n" +
            "0. Salir\n"
}

fun crearMateriaMenu() {

    val nombre = JOptionPane.showInputDialog("Ingrese nombre de materia")
    val creditos = JOptionPane.showInputDialog("Ingrese el # de creditos")
    val codigo = JOptionPane.showInputDialog("Ingrese el codigo")
    val datos = leerArchivo()
    val materiaNueva = crearMateria(nombre, creditos.toInt(), codigo)
    val nuevo = datos + materiaNueva
    escribirEnArchivo(nuevo)
}

fun editarMateriaMenu() {
    val nombre = JOptionPane.showInputDialog("Ingrese nombre de materia a editar")
    val campo = JOptionPane.showInputDialog("Ingrese campo de materia a editar")
    val nuevoValor = JOptionPane.showInputDialog("Ingrese el nuevo valor")
    val datos = leerArchivo()
    val materiaEditada = editarMateria(nombre, campo, nuevoValor, datos)
    escribirEnArchivo(materiaEditada)
}

fun eliminarMateriaMenu() {
    val nombre = JOptionPane.showInputDialog("Ingrese el nombre de la materia a eliminar")
    val datos = leerArchivo()
    val materiaEditada = eliminarMateria(nombre, datos)
    escribirEnArchivo(materiaEditada)
}

fun buscarMateriaMenu() {
    val campo = JOptionPane.showInputDialog("Ingrese campo por el que desea buscar")
    val consulta = JOptionPane.showInputDialog("Ingrese su busqueda")
    val datos = leerArchivo()
    val materiaEncontrada = buscarMateria(campo, consulta, datos)
    val existe = materiaEncontrada.size > 0
    if (existe) {
        var menuRespuesta = ""
        materiaEncontrada.forEach { materia: Materia? ->
            menuRespuesta += "-------------------------------------------------\n" +
                    "Nombre: ${materia?.nombre}\n" +
                    "Fundacion: ${materia?.creditos}\n" +
                    "Numero de estudiantes: ${materia?.estudiantes?.size}\n"
        }
        menuRespuesta += "-------------------------------------------------\n"
        JOptionPane.showMessageDialog(null, menuRespuesta)
    } else {
        JOptionPane.showMessageDialog(null, "Materia no encontrada")
    }
}

fun listarTodoMenu() {
    val total = listarTodo()
    JOptionPane.showMessageDialog(null, total)
}