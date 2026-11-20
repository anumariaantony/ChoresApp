package com.example.choresapp.data

import com.example.choresapp.domain.Chore
import com.example.choresapp.domain.ChoresRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChoresDataRepository(private val dao: ChoresDao) : ChoresRepository {

    override fun getChores(): Flow<List<Chore>> {
        return dao.getAllChores().map { list ->
            list.map { entity ->
                Chore(
                    id = entity.id,
                    choreName = entity.choreName,
                    assignedTo = entity.assignedTo,
                    isDone = entity.isCompleted
                )
            }
        }
    }

    override suspend fun addChore(chore: Chore) {
        dao.insert(ChoresEntity(
            id = chore.id,
            choreName = chore.choreName,
            assignedTo = chore.assignedTo,
            isCompleted = chore.isDone,
        ))
    }

    override suspend fun updateChore(chore: Chore) {
        dao.update(ChoresEntity(
            id = chore.id,
            choreName = chore.choreName,
            assignedTo = chore.assignedTo,
            isCompleted = chore.isDone,
        ))
    }


    override suspend fun deleteChore(chore: Chore) {
        dao.delete(ChoresEntity(
            id = chore.id,
            choreName = chore.choreName,
            assignedTo = chore.assignedTo,
            isCompleted = chore.isDone
        ))
    }
}