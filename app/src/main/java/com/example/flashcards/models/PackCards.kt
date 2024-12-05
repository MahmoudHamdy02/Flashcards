package com.example.flashcards.models

import androidx.room.Embedded
import androidx.room.Relation

data class PackCards(
    @Embedded val pack: Pack,
    @Relation(
        parentColumn = "id",
        entityColumn = "packId"
    )
    val cards: List<WordCard>
)