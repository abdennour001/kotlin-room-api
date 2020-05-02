package com.example.tdm2_data.exercice3

import androidx.room.*
import com.example.tdm2_data.exercice3.models.Intervention

@Dao
interface InterventionDAO {
    @Query("SELECT * FROM intervention")
    fun getInterventions(): MutableList<Intervention>

    @Query("SELECT * FROM intervention WHERE numero = :numero")
    fun getIntervention(numero: Long): List<Intervention>

    @Insert
    fun ajouter(intervention : Intervention)

    @Update
    fun modifier(intervention : Intervention)

    @Delete
    fun supprimer(intervention: Intervention)

}