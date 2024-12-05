package com.example.flashcards.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardsDao {

    @Query("SELECT * FROM wordcard")
    fun getCards(): Flow<List<WordCard>>

    @Query("SELECT * FROM wordcard WHERE id=:id")
    suspend fun getCardById(id: Int): WordCard

    @Upsert
    suspend fun upsertCard(card: WordCard)

    @Delete
    suspend fun deleteCard(card: WordCard)

    @Query("SELECT * FROM pack")
    fun getPacks(): Flow<List<Pack>>

    @Query("SELECT * FROM pack WHERE id=:id")
    suspend fun getPackById(id: Int): Pack

    @Upsert
    suspend fun upsertPack(pack: Pack)

    @Delete
    suspend fun deletePack(pack: Pack)

    @Query("SELECT * FROM WordCard WHERE packId=:packId")
    suspend fun getCardsInPack(packId: Int): List<WordCard>

    @Transaction
    @Query("SELECT * FROM Pack")
    fun getPacksWithCards(): List<PackCards>

}