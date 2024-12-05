package com.example.flashcards.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flashcards.LocalNavigation
import com.example.flashcards.Screen
import com.example.flashcards.ui.theme.DarkGray
import com.example.flashcards.ui.theme.LightGray
import com.example.flashcards.ui.theme.MediumGray
import com.example.flashcards.ui.theme.MediumRed
import com.example.flashcards.ui.theme.White


@Composable
fun CreatePackDialog() {
    val viewModel = hiltViewModel<HomeViewModel>()

    @Composable
    fun DialogButton(text: String, onClickAction: () -> Unit) {
        Button(
            onClick = onClickAction, colors = ButtonDefaults.buttonColors(
                containerColor = DarkGray
            ), shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = text)
        }
    }

    Dialog(onDismissRequest = {
        viewModel.onEvent(HomeEvent.OnTriggerDialog("create"))
    }) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MediumGray),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Add Card Pack",
                    color = White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(30.dp))
                TextField(
                    value = viewModel.state.createPackName, onValueChange = {
                        viewModel.onEvent(
                            HomeEvent.OnTextFieldChange("createPackName", it),
                        )
                    }, textStyle = TextStyle(fontSize = 20.sp)
                )
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DialogButton(text = "Import pack", onClickAction = {})
                    DialogButton(text = "Create pack", onClickAction = {
                        viewModel.onEvent(HomeEvent.CreatePack)
                        viewModel.onEvent(HomeEvent.OnTriggerDialog("create"))
                    })
                }
            }
        }
    }
}

@Composable
fun RenamePackDialog() {
    val viewModel = hiltViewModel<HomeViewModel>()

    @Composable
    fun DialogButton(text: String, onClickAction: () -> Unit) {
        Button(
            onClick = onClickAction, colors = ButtonDefaults.buttonColors(
                containerColor = DarkGray
            ), shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = text)
        }
    }

    Dialog(onDismissRequest = {
        viewModel.onEvent(HomeEvent.OnTriggerDialog("rename"))
    }) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MediumGray),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Rename Card Pack",
                    color = White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = viewModel.state.renamePackName, onValueChange = {
                        viewModel.onEvent(
                            HomeEvent.OnTextFieldChange("renamePackName", it),
                        )
                    }, textStyle = TextStyle(fontSize = 20.sp)
                )
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DialogButton(text = "Rename pack", onClickAction = {
                        viewModel.onEvent(HomeEvent.RenamePack)
                        viewModel.onEvent(HomeEvent.OnTriggerDialog("rename"))
                    })
                }
            }
        }
    }
}

@Composable
fun EditPackDialog() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val navController = LocalNavigation.current

    @Composable
    fun Item(text: String, color: Color = White, modifier: Modifier = Modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .then(modifier)
        ) {
            Text(text = text, color = color, fontSize = 18.sp)
        }
    }

    Dialog(onDismissRequest = {
        viewModel.onEvent(HomeEvent.OnTriggerDialog("edit"))
    }) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MediumGray),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                Item("Add card", modifier = Modifier.clickable {
                    viewModel.onEvent(HomeEvent.OnTriggerDialog("add"))
                })
                Item("Browse cards", modifier = Modifier.clickable {
                    viewModel.onEvent(HomeEvent.OnTriggerDialog("edit"))
                    navController.navigate(Screen.BrowseScreen.route + "/${viewModel.state.selectedPack?.id}")
                })
                Item("Rename pack", modifier = Modifier.clickable {
                    viewModel.onEvent(HomeEvent.OnTriggerDialog("rename"))
                })
                Item("Export pack")
                Item("Delete pack", MediumRed, modifier = Modifier.clickable {
                    viewModel.onEvent(HomeEvent.OnTriggerDialog("delete"))
                })
            }
        }
    }
}

@Composable
fun AddWordDialog() {
    val viewModel = hiltViewModel<HomeViewModel>()

    @Composable
    fun Input(value: String, key: String) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable { }) {
            BasicTextField(
                value = value,
                onValueChange = { viewModel.onEvent(HomeEvent.OnTextFieldChange(key, it)) },
                textStyle = TextStyle(fontSize = 18.sp, color = White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(LightGray)
                    .padding(8.dp)
            )
        }
    }

    @Composable
    fun Label(text: String) {
        Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = White)
    }

    Dialog(onDismissRequest = {
        viewModel.onEvent(HomeEvent.OnTriggerDialog("add"))
    }) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MediumGray),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                Label(text = "Source Word")
                Input(value = viewModel.state.addSourceWord, key = "addSourceWord")
                Label(text = "Source Description")
                Input(value = viewModel.state.addSourceWordBody, key = "addSourceWordBody")
                Label(text = "Target Word")
                Input(value = viewModel.state.addTargetWord, key = "addTargetWord")
                Label(text = "Target Description")
                Input(value = viewModel.state.addTargetWordBody, key = "addTargetWordBody")
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            viewModel.onEvent(HomeEvent.InsertCard)
                            viewModel.onEvent(HomeEvent.ToggleSnackbar)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DarkGray),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Add Word")
                    }
                }
            }
        }
    }
}

@Composable
fun DeletePackDialog() {
    val viewModel = hiltViewModel<HomeViewModel>()

    @Composable
    fun DialogButton(text: String, onClickAction: () -> Unit, color: Color = DarkGray) {
        Button(
            onClick = onClickAction, colors = ButtonDefaults.buttonColors(
                containerColor = color
            ), shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = text)
        }
    }

    Dialog(onDismissRequest = {
        viewModel.onEvent(HomeEvent.OnTriggerDialog("delete"))
    }) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MediumGray),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Delete Card Pack",
                    color = White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DialogButton(text = "Delete pack", color = MediumRed, onClickAction = {
                        viewModel.onEvent(HomeEvent.DeletePack)
                        viewModel.onEvent(HomeEvent.OnTriggerDialog("delete"))
                    })
                }
            }
        }
    }
}