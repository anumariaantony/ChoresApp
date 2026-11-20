package com.example.choresapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import androidx.room.util.copy
import com.example.choresapp.data.ChoresDataRepository
import com.example.choresapp.data.ChoresDatabase
import com.example.choresapp.domain.AddChoreUseCase
import com.example.choresapp.domain.Chore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

open class ChoresViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        ChoresDatabase::class.java,
        "chores_database"
    ).build()

    private val choresRepository = ChoresDataRepository(db.choresDao())

    private val addChoreUseCase = AddChoreUseCase(choresRepository)

    open val chores = choresRepository.getChores().stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )

    fun addChore(choreName: String, assignedTo: String) {
        viewModelScope.launch {
            addChoreUseCase.execute(choreName, assignedTo)
        }
    }

    fun toggleChoreDone(chore: Chore) {
        viewModelScope.launch {
            choresRepository.updateChore(chore.copy(isDone = !chore.isDone))

        }
    }

    fun editChore(chore : Chore, newChoreName: String, newAssignedTo: String) {
        viewModelScope.launch {
            if(newChoreName.isNotBlank() /*&& newAssignedTo.isNotBlank()*/) {
                choresRepository.updateChore(chore.copy(choreName = newChoreName, assignedTo = newAssignedTo))
            }
        }
    }

    fun deleteChore(chore: Chore) {
        viewModelScope.launch {
            choresRepository.deleteChore(chore)
        }
    }

}