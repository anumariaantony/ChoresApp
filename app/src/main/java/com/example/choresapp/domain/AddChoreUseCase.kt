package com.example.choresapp.domain

class AddChoreUseCase(private val choresRepository: ChoresRepository){
    suspend fun execute(description: String, assignedTo: String) {
        val chore = Chore( id = 0,choreName= description, assignedTo = assignedTo, isDone = false)
        choresRepository.addChore(chore)
    }
}