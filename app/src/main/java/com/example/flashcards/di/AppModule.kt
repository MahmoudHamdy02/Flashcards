package com.example.flashcards.di

import android.app.Application
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.flashcards.models.FlashcardsDao
import com.example.flashcards.models.FlashcardsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDb(applicationContext : Application): FlashcardsDatabase {
        val db by lazy {
            Room.databaseBuilder(
                applicationContext,
                FlashcardsDatabase::class.java,
                "flashcards.db"
            ).build()
        }
        return db
    }

    @Provides
    @Singleton
    fun provideDao(db: FlashcardsDatabase) : FlashcardsDao {
        return db.dao
    }
}