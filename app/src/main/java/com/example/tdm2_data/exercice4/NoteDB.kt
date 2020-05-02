package com.example.tdm2_data.exercice4

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tdm2_data.exercice4.models.Note


@Database(entities = arrayOf(Note::class), version = 2)
abstract class NoteDB : RoomDatabase() {
    abstract fun noteDAO(): NoteDAO

    companion object {
        private var instance: NoteDB? = null

        fun getInstance(context: Context): NoteDB? {
            if (instance == null) {

                instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDB::class.java, "note.db")
                    .build()

            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }
}