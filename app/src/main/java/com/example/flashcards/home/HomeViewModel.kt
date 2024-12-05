package com.example.flashcards.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards.models.FlashcardsDao
import com.example.flashcards.models.Pack
import com.example.flashcards.models.WordCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dao: FlashcardsDao
) : ViewModel() {
    var state by mutableStateOf(HomeState())

    init {
        viewModelScope.launch {
            dao.getPacks().collectLatest {
                state = state.copy(packs = it)
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnTriggerDialog -> {
                state = when (event.key) {
                    "create" -> state.copy(showCreateDialog = !state.showCreateDialog, createPackName = "")
                    "edit" -> state.copy(showEditDialog = !state.showEditDialog)
                    "add" -> state.copy(showAddDialog = !state.showAddDialog)
                    "delete" -> state.copy(showDeleteDialog = !state.showDeleteDialog)
                    "rename" ->
                        state.copy(
                            showRenameDialog = !state.showRenameDialog,
                            renamePackName = state.selectedPack?.title ?: ""
                        )

                    else -> state
                }
            }

            is HomeEvent.OnEditPack -> {
                state = state.copy(selectedPack = event.pack)
            }

            is HomeEvent.OnTextFieldChange -> {
                state = when (event.key) {
                    "createPackName" -> state.copy(createPackName = event.value)
                    "renamePackName" -> state.copy(renamePackName = event.value)
                    "addSourceWord" -> state.copy(addSourceWord = event.value)
                    "addSourceWordBody" -> state.copy(addSourceWordBody = event.value)
                    "addTargetWord" -> state.copy(addTargetWord = event.value)
                    "addTargetWordBody" -> state.copy(addTargetWordBody = event.value)
                    else -> state
                }
            }

            is HomeEvent.CreatePack -> {
                viewModelScope.launch {
                    dao.upsertPack(Pack(title = state.createPackName))
                    state = state.copy(createPackName = "", showAddDialog = false)
                }
            }

            is HomeEvent.RenamePack -> {
                viewModelScope.launch {
                    if (state.selectedPack != null) {
                        dao.upsertPack(
                            Pack(
                                id = state.selectedPack!!.id,
                                title = state.renamePackName
                            )
                        )
                        state = state.copy(
                            renamePackName = "",
                            showRenameDialog = false,
                            showEditDialog = false
                        )
                    }
                }
            }

            is HomeEvent.DeletePack -> {
                viewModelScope.launch {
                    if (state.selectedPack != null) {
                        dao.deletePack(state.selectedPack!!)
                        state = state.copy(showDeleteDialog = false, showEditDialog = false)
                    }
                }
            }

            is HomeEvent.InsertCard -> {
                viewModelScope.launch {
                    if (state.selectedPack != null) {
                        val cards = dao.getCardsInPack(state.selectedPack!!.id)
                        val nextIndex =
                            if (cards.isNotEmpty()) cards.maxBy { it.index }.index + 1 else 0
                        dao.upsertCard(
                            WordCard(
                                packId = state.selectedPack!!.id,
                                source = state.addSourceWord,
                                sourceBody = state.addSourceWordBody,
                                target = state.addTargetWord,
                                targetBody = state.addTargetWordBody,
                                index = nextIndex
                            )
                        )
                        state = state.copy(
                            addSourceWord = "",
                            addSourceWordBody = "",
                            addTargetWord = "",
                            addTargetWordBody = "",
                            showEditDialog = false,
                            showAddDialog = false
                        )
                    }
                }
            }
            is HomeEvent.ToggleSnackbar -> {
                state = state.copy(cardAddedSnackbarVisible = !state.cardAddedSnackbarVisible)
            }
        }
    }
}

data class HomeState(
    val showCreateDialog: Boolean = false,
    val showEditDialog: Boolean = false,
    val showAddDialog: Boolean = false,
    val showRenameDialog: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val createPackName: String = "",
    val renamePackName: String = "",
    val packs: List<Pack> = emptyList(),
    val selectedPack: Pack? = null,
    val addSourceWord: String = "",
    val addSourceWordBody: String = "",
    val addTargetWord: String = "",
    val addTargetWordBody: String = "",
    val cardAddedSnackbarVisible: Boolean = false
)

sealed class HomeEvent {
    data class OnTriggerDialog(val key: String) : HomeEvent()
    data class OnEditPack(val pack: Pack) : HomeEvent()
    data class OnTextFieldChange(val key: String, val value: String) : HomeEvent()
    data object RenamePack : HomeEvent()
    data object CreatePack : HomeEvent()
    data object DeletePack : HomeEvent()
    data object InsertCard : HomeEvent()
    data object ToggleSnackbar : HomeEvent()
}
