package com.example.flashcards.browse

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards.LocalNavigation
import com.example.flashcards.models.FlashcardsDao
import com.example.flashcards.models.WordCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val dao: FlashcardsDao
) : ViewModel() {
    var state by mutableStateOf(BrowseState())

    fun onEvent(event: BrowseEvent) {
        when (event) {
            is BrowseEvent.Load -> {
                viewModelScope.launch {
                    val cards = dao.getCardsInPack(event.id)
                    state = state.copy(
                        cards = cards,
                    )
                }
            }
        }
    }
}

data class BrowseState(
    val cards: List<WordCard> = emptyList(),
)

sealed class BrowseEvent {
    data class Load(val id: Int) : BrowseEvent()
}