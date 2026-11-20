package com.example.choresapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ChoresDao {

    @Query("SELECT * FROM chores")
    fun getAllChores(): Flow<List<ChoresEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (chore : ChoresEntity)

    @Update
    suspend fun update (chore : ChoresEntity)

    @Delete
    suspend fun delete (chore : ChoresEntity)

}
