package com.example.choresapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ChoresEntity::class], version = 1, exportSchema = false)
abstract class ChoresDatabase : RoomDatabase() {
    abstract fun choresDao(): ChoresDao
}