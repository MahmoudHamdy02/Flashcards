package com.example.flashcards.pack

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.flashcards.LocalNavigation
import com.example.flashcards.ui.theme.DarkGray
import com.example.flashcards.ui.theme.LightGray
import com.example.flashcards.ui.theme.MediumGray
import com.example.flashcards.ui.theme.White
import com.example.flashcards.pack.PackEvent
import com.example.flashcards.pack.PackViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackScreen(packId: Int) {
    val viewModel = hiltViewModel<PackViewModel>()
    viewModel.onEvent(PackEvent.Load(packId))
    val navController = LocalNavigation.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Flashcards")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MediumGray,
                    titleContentColor = White
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Go back",
                            tint = White
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(DarkGray)
        ) {
            Text(
                text = "${viewModel.state.currentCard?.index?.plus(1)} / ${viewModel.state.cards.size}",
                fontSize = 20.sp,
                color = White,
                modifier = Modifier.padding(top = 25.dp)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 25.dp)
                    .border(width = 5.dp, color = White, RoundedCornerShape(20.dp))
                    .fillMaxWidth(0.9f)
                    .height(400.dp)
                    .clickable { viewModel.onEvent(PackEvent.Reveal) }
            ) {
                Text(
                    text = viewModel.state.currentCard?.source ?: "",
                    fontSize = 32.sp,
                    color = White
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = viewModel.state.currentCard?.sourceBody ?: "",
                    fontSize = 20.sp,
                    color = White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(40.dp))
                Divider(modifier = Modifier.fillMaxWidth(0.9f))
                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = if (viewModel.state.revealed) viewModel.state.currentCard?.target
                        ?: "" else "",
                    fontSize = 32.sp,
                    color = White
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = if (viewModel.state.revealed) viewModel.state.currentCard?.targetBody
                        ?: "" else "",
                    fontSize = 20.sp,
                    color = White,
                    textAlign = TextAlign.Center
                )
            }
            Text(text = "Tap to reveal", color = White)
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(top = 50.dp)
            ) {
                Button(
                    enabled = !viewModel.state.firstCardSelected,
                    onClick = { viewModel.onEvent(PackEvent.Previous) },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,
                        contentColor = DarkGray,
                        disabledContainerColor = LightGray
                    )
                ) {
                    Text(text = "Previous card", fontSize = 20.sp, color = DarkGray)
                }
                Button(
                    enabled = !viewModel.state.lastCardSelected,
                    onClick = { viewModel.onEvent(PackEvent.Next) },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,
                        contentColor = DarkGray,
                        disabledContainerColor = LightGray
                    )
                ) {
                    Text(text = "Next card", fontSize = 20.sp, color = DarkGray)
                }
            }
        }
    }
}