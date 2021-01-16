package com.example.api_universidad.data.roomdatabase.Model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.api_universidad.MateriaActivity
import com.example.api_universidad.data.roomdatabase.Entity.EstudianteEntity
import com.example.api_universidad.data.roomdatabase.Entity.MateriaEntity
import com.example.api_universidad.data.roomdatabase.Entity.relations.MateriaEstudiantes
import com.example.api_universidad.data.roomdatabase.Repository.MateriaRepository
import com.huawei.todolist.utils.subscribeOnBackground

class MateriaViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = MateriaRepository(app)
    private val allMaterias = repository.getAllMaterias()

    fun insert(materia: MateriaEntity) {
        repository.insert(materia)
    }

    fun update(materia: MateriaEntity) {
        repository.update(materia)
    }

    fun updateEstudiante(estudiante: EstudianteEntity) {
        repository.updateEstudiante(estudiante)
    }

    fun delete(materia: MateriaEntity) {
        repository.delete(materia)
    }

    fun deleteEstudiante(estudiante: EstudianteEntity) {
        repository.deleteEstudiante(estudiante)
    }

    fun deleteAllMaterias() {
        repository.deleteAllMaterias()
    }

    fun getAllMaterias(): LiveData<Array<MateriaEntity>> {
        return allMaterias
    }

    fun getOneMateria(id: Int): LiveData<MateriaEntity> {
        return repository.getOneMateria(id)
    }

    fun getMateriaEstudiantes(id: Int): LiveData<List<MateriaEstudiantes>> {
        return repository.getMateriaEstudiantes(id)
    }

    fun insertEstudiante(estudiante: EstudianteEntity) {
        subscribeOnBackground {
            repository.insertEstudiante(estudiante)
        }

    }


}