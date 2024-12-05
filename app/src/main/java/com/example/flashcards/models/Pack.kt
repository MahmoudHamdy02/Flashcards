package com.example.flashcards.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity
class Pack(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String
)
