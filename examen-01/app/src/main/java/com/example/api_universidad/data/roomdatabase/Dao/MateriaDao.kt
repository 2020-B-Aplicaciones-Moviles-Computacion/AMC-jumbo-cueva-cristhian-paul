package com.example.api_universidad.data.roomdatabase.Dao
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.api_universidad.data.roomdatabase.Entity.EstudianteEntity
import com.example.api_universidad.data.roomdatabase.Entity.MateriaEntity
import com.example.api_universidad.data.roomdatabase.Entity.relations.MateriaEstudiantes


@Dao
interface MateriaDao {

    @Insert
    fun insert(materia: MateriaEntity)

    @Update
    fun update(materia: MateriaEntity)

    @Update
    fun updateEstudiante(estudiante: EstudianteEntity)

    @Insert()
    fun insertEstudiante(estudiante: EstudianteEntity)

    @Transaction
    @Query("SELECT * FROM materia WHERE id = :id")
    fun getMateriaEstudiantes(id: Int): LiveData<List<MateriaEstudiantes>>

    @Delete
    fun delete(materia: MateriaEntity)

    @Delete
    fun deleteEstudiante(estudiante: EstudianteEntity)

    @Query("delete from materia")
    fun deleteAllMaterias()

    @Query("SELECT * FROM MATERIA WHERE id=:id ")
    fun getOneMateria(id: Int): LiveData<MateriaEntity>

    @Query("select * from materia order by  id")
    fun getAllMaterias(): LiveData<Array<MateriaEntity>>
}