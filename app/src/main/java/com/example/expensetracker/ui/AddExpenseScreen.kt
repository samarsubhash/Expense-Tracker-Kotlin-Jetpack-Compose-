package com.example.expensetracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.data.Expense
import com.example.expensetracker.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    onSaveClick: (Expense) -> Unit,
    onBackClick: () -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("Expense") }
    var category by remember { mutableStateOf("Food") }
    
    val categories = listOf("Food", "Travel", "Shopping", "Bills", "Health", "Other")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Transaction", color = White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Black)
            )
        },
        containerColor = Black
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Type Segmented Control
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(CardBackground),
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf("Expense", "Income").forEach { selectedType ->
                    val isSelected = type == selectedType
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(4.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .background(if (isSelected) NeonPurple else Color.Transparent)
                            .clickable { type = selectedType },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = selectedType,
                            color = if (isSelected) White else TextGray,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Amount Input
            Text("Enter Amount", color = TextGray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = White,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonCyan,
                    unfocusedBorderColor = DarkGray,
                    cursorColor = NeonCyan
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                prefix = { Text("₹", fontSize = 32.sp, color = White, fontWeight = FontWeight.Bold) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Comment Input
            Text("Note (Optional)", color = TextGray, fontSize = 14.sp, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(8.dp))
            var comment by remember { mutableStateOf("") }
            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                textStyle = LocalTextStyle.current.copy(color = White),
                placeholder = { Text("Add a note...", color = TextGray) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonPurple,
                    unfocusedBorderColor = DarkGray,
                    cursorColor = NeonPurple
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Category Selection
            Text("Select Category", color = TextGray, fontSize = 14.sp, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories) { cat ->
                    val isSelected = category == cat
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) NeonPurple.copy(alpha = 0.2f) else CardBackground)
                            .border(
                                width = 1.dp,
                                color = if (isSelected) NeonPurple else Color.Transparent,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { category = cat }
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = cat,
                            color = if (isSelected) NeonPurple else TextGray,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Save Button
            Button(
                onClick = {
                    val amountVal = amount.toDoubleOrNull()
                    if (amountVal != null) {
                        onSaveClick(
                            Expense(
                                amount = amountVal,
                                type = type,
                                category = category,
                                date = System.currentTimeMillis(),
                                comment = comment
                            )
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(PurpleGradient, RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues()
            ) {
                Text("Save Transaction", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = White)
            }
        }
    }
}
