package com.example.choresapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.choresapp.data.UserType
import com.example.choresapp.presentation.ui.theme.ColorSupport
import com.example.choresapp.presentation.ui.theme.Purple40
import com.example.choresapp.presentation.viewmodel.ChoresViewModel

@Composable
fun ChoresScreen(
    choresViewModel: ChoresViewModel,
    username: String,
    userType: UserType,
    onLogout: () -> Unit
) {

    var chore by remember { mutableStateOf("") }
    val choresList by choresViewModel.chores.collectAsStateWithLifecycle()
    var assignedTo by remember { mutableStateOf("") }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(ColorSupport.gradient))
            .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome, $username",
                color = Purple40,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Input Box - Only for Parents
            if (userType == UserType.PARENT) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.15f), RoundedCornerShape(28.dp))
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = chore,
                        onValueChange = { chore = it },
                        placeholder = {
                            Text(
                                text = "Enter Chore",
                                color = Purple40.copy(alpha = 0.6f),
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Purple40,
                            unfocusedBorderColor = Purple40.copy(alpha = 0.5f),
                            focusedTextColor = Purple40,
                            unfocusedTextColor = Purple40,
                            cursorColor = Purple40
                        )
                    )
                    OutlinedTextField(
                        value = assignedTo,
                        onValueChange = { assignedTo = it },
                        placeholder = {
                            Text(
                                text = "Enter Assignee",
                                color = Purple40.copy(alpha = 0.6f),
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Purple40,
                            unfocusedBorderColor = Purple40.copy(alpha = 0.5f),
                            focusedTextColor = Purple40,
                            unfocusedTextColor = Purple40,
                            cursorColor = Purple40
                        )
                    )
                    Button(
                        onClick = {
                            if (chore.isNotBlank()) {
                                choresViewModel.addChore(chore, assignedTo)
                                chore = ""
                                assignedTo = ""
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Purple40
                        )
                    ) {
                        Text("Add Chore", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            // List Box
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.White.copy(alpha = 0.15f), RoundedCornerShape(28.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Chores List",
                    style = MaterialTheme.typography.titleMedium,
                    color = Purple40,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 12.dp, start = 4.dp)
                        .align(Alignment.CenterHorizontally)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(choresList) { choreItem ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White.copy(alpha = 0.9f)
                            ),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            var isEditing by remember { mutableStateOf(false) }
                            var newTitle by remember { mutableStateOf(choreItem.choreName) }
                            var newAssignedTo by remember { mutableStateOf(choreItem.assignedTo) }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    if (!isEditing) {
                                        Checkbox(
                                            checked = choreItem.isDone,
                                            onCheckedChange = { choresViewModel.toggleChoreDone(choreItem) },
                                            colors = CheckboxDefaults.colors(Purple40)
                                        )
                                        Column(modifier = Modifier.padding(start = 8.dp)) {
                                            Text(
                                                text = choreItem.choreName,
                                                style = if (choreItem.isDone)
                                                    LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough)
                                                else
                                                    LocalTextStyle.current,
                                                color = Purple40
                                            )
                                            Text(
                                                text = "Assigned to: ${choreItem.assignedTo}",
                                                style = if (choreItem.isDone)
                                                    LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough)
                                                else
                                                    MaterialTheme.typography.bodySmall,
                                                color = Purple40.copy(alpha = 0.7f)
                                            )
                                        }
                                    } else {
                                        Column(
                                            modifier = Modifier.weight(1f),
                                            verticalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            OutlinedTextField(
                                                value = newTitle,
                                                onValueChange = { newTitle = it },
                                                modifier = Modifier.fillMaxWidth(),
                                                shape = RoundedCornerShape(12.dp),
                                                colors = OutlinedTextFieldDefaults.colors(
                                                    focusedBorderColor = Purple40,
                                                    unfocusedBorderColor = Purple40.copy(alpha = 0.5f)
                                                )
                                            )
                                            OutlinedTextField(
                                                value = newAssignedTo,
                                                onValueChange = { newAssignedTo = it },
                                                modifier = Modifier.fillMaxWidth(),
                                                shape = RoundedCornerShape(12.dp),
                                                colors = OutlinedTextFieldDefaults.colors(
                                                    focusedBorderColor = Purple40,
                                                    unfocusedBorderColor = Purple40.copy(alpha = 0.5f)
                                                )
                                            )
                                        }
                                        IconButton(onClick = {
                                            choresViewModel.editChore(choreItem, newTitle, newAssignedTo)
                                            isEditing = false
                                        }) {
                                            Icon(Icons.Default.CheckCircle, contentDescription = "Save", tint = Purple40)
                                        }
                                    }
                                }
                                if (!isEditing && userType == UserType.PARENT) {
                                    Row {
                                        IconButton(onClick = {
                                            newTitle = choreItem.choreName
                                            newAssignedTo = choreItem.assignedTo
                                            isEditing = true
                                        }) {
                                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Purple40)
                                        }
                                        IconButton(onClick = { choresViewModel.deleteChore(choreItem) }) {
                                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Purple40)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = Purple40
                )
            ) {
                Text("Logout", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}
