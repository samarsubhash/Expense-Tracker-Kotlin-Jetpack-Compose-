package com.example.expensetracker.data

data class Expense(
    val id: Int = 0,
    val amount: Double,
    val type: String, // "Income" or "Expense"
    val category: String, // "Food", "Travel", etc.
    val date: Long, // Timestamp
    val comment: String = "" // Optional note
)
