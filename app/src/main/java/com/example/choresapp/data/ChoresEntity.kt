package com.example.choresapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chores")
data class ChoresEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val choreName: String,
    val assignedTo: String,
    val isCompleted: Boolean = false
)
