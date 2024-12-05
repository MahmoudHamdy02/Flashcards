package com.example.flashcards.pack

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards.models.FlashcardsDao
import com.example.flashcards.models.WordCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PackViewModel @Inject constructor(
    private val dao: FlashcardsDao
) : ViewModel() {
    var state by mutableStateOf(PackViewState())
    private var index by mutableIntStateOf(0)

    fun onEvent(event: PackEvent) {
        when (event) {
            is PackEvent.Load -> {
                viewModelScope.launch {
                    val cards = dao.getCardsInPack(event.id)
                    state = state.copy(
                        cards = cards,
                        currentCard = if (cards.isNotEmpty()) cards[index] else null,
                        lastCardSelected = cards.size == 1
                    )
                }
            }

            is PackEvent.Reveal -> {
                state = state.copy(revealed = true)
            }

            is PackEvent.Next -> {
                index += 1
                state = state.copy(
                    revealed = false,
                    currentCard = state.cards[index],
                    lastCardSelected = (index + 1) == state.cards.size,
                    firstCardSelected = index == 0
                )
            }

            is PackEvent.Previous -> {
                index -= 1
                state = state.copy(
                    revealed = false,
                    currentCard = state.cards[index],
                    lastCardSelected = (index + 1) == state.cards.size,
                    firstCardSelected = index == 0
                )
            }

        }
    }
}

data class PackViewState(
    val cards: List<WordCard> = emptyList(),
    val currentCard: WordCard? = null,
    val revealed: Boolean = false,
    val firstCardSelected: Boolean = true,
    val lastCardSelected: Boolean = false
)

sealed class PackEvent {
    data class Load(val id: Int) : PackEvent()
    data object Reveal : PackEvent()
    data object Next : PackEvent()
    data object Previous : PackEvent()
}