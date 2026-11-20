package com.example.choresapp.domain

import kotlinx.coroutines.flow.Flow

interface ChoresRepository {

    fun getChores(): Flow<List<Chore>>
    suspend fun addChore(chore: Chore)
    suspend fun updateChore(chore: Chore)
    suspend fun deleteChore(chore: Chore)

}