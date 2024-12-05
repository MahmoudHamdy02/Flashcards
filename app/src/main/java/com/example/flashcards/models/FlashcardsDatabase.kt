package com.example.flashcards.models

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Pack::class, WordCard::class],
    version = 1
)
abstract class FlashcardsDatabase : RoomDatabase() {
    abstract val dao: FlashcardsDao
}