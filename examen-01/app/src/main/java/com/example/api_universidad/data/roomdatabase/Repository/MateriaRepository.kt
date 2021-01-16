package com.example.api_universidad.data.roomdatabase.Repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.api_universidad.data.DatabaseService
import com.example.api_universidad.data.roomdatabase.Dao.MateriaDao
import com.example.api_universidad.data.roomdatabase.Entity.EstudianteEntity
import com.example.api_universidad.data.roomdatabase.Entity.MateriaEntity
import com.example.api_universidad.data.roomdatabase.Entity.relations.MateriaEstudiantes
import com.huawei.todolist.utils.subscribeOnBackground

class MateriaRepository (application: Application) {

    private var materiaDao: MateriaDao
    private var allMaterias: LiveData<Array<MateriaEntity>>
    private val database = DatabaseService.getInstance(application)

    init {
        materiaDao = database.materiaDao()
        allMaterias = materiaDao.getAllMaterias()
    }

    fun insert(materia: MateriaEntity) {
        subscribeOnBackground {
            materiaDao.insert(materia)
        }

    }

    fun update(materia: MateriaEntity) {
        subscribeOnBackground {
            materiaDao.update(materia)
        }
    }

    fun updateEstudiante(estudiante: EstudianteEntity) {
        subscribeOnBackground {
            materiaDao.updateEstudiante(estudiante)
        }
    }


    fun delete(materia: MateriaEntity) {
        subscribeOnBackground {
            materiaDao.delete(materia)

        }
    }

    fun deleteEstudiante(estudiante: EstudianteEntity) {
        subscribeOnBackground {
            materiaDao.deleteEstudiante(estudiante)

        }
    }

    fun deleteAllMaterias() {
        subscribeOnBackground {
            materiaDao.deleteAllMaterias()
        }
    }

    fun getAllMaterias(): LiveData<Array<MateriaEntity>> {
        return allMaterias
    }

    fun getOneMateria(id: Int): LiveData<MateriaEntity> {
        return materiaDao.getOneMateria(id)
    }

    fun getMateriaEstudiantes(id: Int): LiveData<List<MateriaEstudiantes>> {
        return materiaDao.getMateriaEstudiantes(id)
    }

    fun insertEstudiante(estudiante: EstudianteEntity) {
        subscribeOnBackground {
            materiaDao.insertEstudiante(estudiante)
        }

    }

}