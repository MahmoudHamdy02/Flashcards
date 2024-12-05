package com.example.flashcards.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class WordCard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val packId: Int,
    val index: Int, // Index (order) of the card in its respective pack
    val source: String,
    val sourceBody: String?,
    val target: String,
    val targetBody: String?
)