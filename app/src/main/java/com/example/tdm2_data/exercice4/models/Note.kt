package com.example.tdm2_data.exercice4.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Note (
    @PrimaryKey(autoGenerate = true) var id: Int,

    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "color") var color: String,
    @ColumnInfo(name = "body") var body: String
) : Serializable {
    constructor():this(0,"","", "", "")
}