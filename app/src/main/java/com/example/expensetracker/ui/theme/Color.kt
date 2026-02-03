package com.example.expensetracker.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush

val Black = Color(0xFF000000)
val SurfaceBlack = Color(0xFF121212)
val CardBackground = Color(0xFF1E1E1E)

// Neon Accents
val NeonPurple = Color(0xFFBB86FC)
val NeonCyan = Color(0xFF03DAC5)
val NeonBlue = Color(0xFF3700B3)

// Text
val TextWhite = Color(0xFFEEEEEE)
val TextGray = Color(0xFFB0B0B0)

// Expense/Income
val RedExpense = Color(0xFFFF5252)
val GreenIncome = Color(0xFF69F0AE)

// Gradients
val PurpleGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFF6200EE), Color(0xFFBB86FC))
)

val DarkGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFF121212), Color(0xFF000000))
)

val PurplePrimary = NeonPurple
val DarkGray = CardBackground
val White = TextWhite
val LightGray = TextGray
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)