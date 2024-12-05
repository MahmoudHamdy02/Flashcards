package com.example.flashcards.browse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flashcards.LocalNavigation
import com.example.flashcards.ui.theme.DarkGray
import com.example.flashcards.ui.theme.LightGray
import com.example.flashcards.ui.theme.MediumGray
import com.example.flashcards.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseScreen(packId: Int) {
    val viewModel = hiltViewModel<BrowseViewModel>()
    viewModel.onEvent(BrowseEvent.Load(packId))
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGray)
                .padding(paddingValues)
        ) {
            items(viewModel.state.cards) { card ->
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(4.dp)
                        .background(MediumGray, RoundedCornerShape(10.dp))
//                        .padding(8.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(15f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Drag card",
                            tint = White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(40f)
                    ) {
                        Text(
                            text = card.source,
                            fontSize = 20.sp,
                            color = White,
                            fontWeight = FontWeight.SemiBold,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = card.sourceBody ?: "",
                            fontSize = 16.sp,
                            color = White,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(5f)
                    ) {
                        Divider(
                            modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .width(1.dp)
                                .fillMaxHeight()
                        )
                    }

                    Column(
                        modifier = Modifier
                            .weight(40f)
                    ) {
                        Text(
                            text = card.target,
                            fontSize = 20.sp,
                            color = White,
                            fontWeight = FontWeight.SemiBold,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = card.targetBody ?: "",
                            fontSize = 16.sp,
                            color = White,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}