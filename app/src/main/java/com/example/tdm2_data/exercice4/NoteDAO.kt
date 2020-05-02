package com.example.tdm2_data.exercice4

import androidx.room.*
import com.example.tdm2_data.exercice4.models.Note

@Dao
interface NoteDAO {
    @Query("SELECT * FROM note")
    fun getNotes(): MutableList<Note>

    @Query("SELECT * FROM note WHERE id = :id")
    fun getNote(id: Int): List<Note>

    @Insert
    fun ajouter(note : Note)

    @Update
    fun modifier(note : Note)

    @Delete
    fun supprimer(note: Note)

}