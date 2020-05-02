package com.example.tdm2_data.exercice3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tdm2_data.exercice3.models.Intervention


@Database(entities = arrayOf(Intervention::class), version = 1)
abstract class InterventionDB : RoomDatabase() {
    abstract fun interventionDAO(): InterventionDAO

    companion object {
        private var instance: InterventionDB? = null

        fun getInstance(context: Context): InterventionDB? {
            if (instance == null) {

                instance = Room.databaseBuilder(context.getApplicationContext(),
                    InterventionDB::class.java, "intervention.db")
                    .build()

            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }
}