package com.example.tdm2_data.exercice3.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Intervention (
    @PrimaryKey var numero: Long,
    @ColumnInfo(name = "nom_plombier") var nom_plombier: String,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "date") var date: String
){
    constructor():this(0,"","", date="")
}