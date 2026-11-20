package com.example.choresapp.domain

data class Chore(
    val id: Int,
    val choreName: String,
    val assignedTo: String,
    val isDone: Boolean = false
)