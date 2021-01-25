package com.example.makenotes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
class Note(@ColumnInfo(name = "title") val title: String,@ColumnInfo(name = "text") val text: String,@ColumnInfo(name = "date") val date: String) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}