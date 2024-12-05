package com.example.flashcards.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flashcards.LocalNavigation
import com.example.flashcards.Screen
import com.example.flashcards.ui.theme.Black
import com.example.flashcards.ui.theme.DarkGray
import com.example.flashcards.ui.theme.MediumGray
import com.example.flashcards.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = LocalNavigation.current

    LaunchedEffect(key1 = viewModel.state.cardAddedSnackbarVisible) {
        if (viewModel.state.cardAddedSnackbarVisible) {
            when (snackbarHostState.showSnackbar("Card Added!")) {
                SnackbarResult.Dismissed -> viewModel.onEvent(HomeEvent.ToggleSnackbar)
                else -> {}
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(HomeEvent.OnTriggerDialog("create"))
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Add, contentDescription = "Add card pack"
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Flashcards")
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MediumGray, titleContentColor = Color.White
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        if (viewModel.state.showCreateDialog) {
            CreatePackDialog()
        }
        if (viewModel.state.showEditDialog) {
            EditPackDialog()
        }
        if (viewModel.state.showAddDialog) {
            AddWordDialog()
        }
        if (viewModel.state.showRenameDialog) {
            RenamePackDialog()
        }
        if (viewModel.state.showDeleteDialog) {
            DeletePackDialog()
        }

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(DarkGray)
        ) {
            items(viewModel.state.packs) { pack ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color(0xFF555555))
                        .fillMaxWidth()
                        .height(50.dp)
                        .combinedClickable(onClick = { navController.navigate(Screen.PackScreen.route + "/" + pack.id) },
                            onLongClick = {
                                viewModel.onEvent(HomeEvent.OnTriggerDialog("edit"))
                                viewModel.onEvent(HomeEvent.OnEditPack(pack))
                            })
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = pack.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                }
                Divider(color = Black)
            }
        }
    }
}
