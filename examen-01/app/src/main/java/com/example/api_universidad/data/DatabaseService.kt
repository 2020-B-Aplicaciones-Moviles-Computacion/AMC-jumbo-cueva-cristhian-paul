package com.example.api_universidad.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.api_universidad.data.roomdatabase.Dao.MateriaDao
import com.example.api_universidad.data.roomdatabase.Entity.EstudianteEntity
import com.example.api_universidad.data.roomdatabase.Entity.MateriaEntity
import com.huawei.todolist.utils.subscribeOnBackground

@Database(entities = arrayOf(MateriaEntity::class, EstudianteEntity::class), version = 1, exportSchema = false)
public abstract class DatabaseService : RoomDatabase() {

    abstract fun materiaDao(): MateriaDao

    companion object {
        private var instance: DatabaseService? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseService {
            if(instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext, DatabaseService::class.java,
                    "materia_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

            return instance!!

        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }

        private fun populateDatabase(db: DatabaseService) {
            val materiaDao = db.materiaDao()
            subscribeOnBackground {
                materiaDao.insert(MateriaEntity("Programacion", "C", 1, 1))
                materiaDao.insert(MateriaEntity("Seguridad", "B", 2, 1))
                materiaDao.insert(MateriaEntity("Web", "A", 3, 1))
                materiaDao.insertEstudiante(EstudianteEntity("Cristhian", "1996-02-02", "M", 1.47,1 , 1.54, 1.48, "imagen", "crisjc", 1 ))
                materiaDao.insertEstudiante(EstudianteEntity("Carlos", "1996-02-02", "M", 1.47,1 , 1.54, 1.48, "imagen", "crisjc", 2 ))
                materiaDao.insertEstudiante(EstudianteEntity("Manuel", "1996-02-02", "M", 1.47,1 , 1.54, 1.48, "imagen", "crisjc", 3 ))
            }

        }
    }
}