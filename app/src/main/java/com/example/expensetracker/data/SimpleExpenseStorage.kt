package com.example.expensetracker.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class SimpleExpenseStorage(private val context: Context) {
    private val fileName = "expenses.json"

    fun saveExpenses(expenses: List<Expense>) {
        try {
            val jsonArray = JSONArray()
            expenses.forEach { expense ->
                val jsonObject = JSONObject().apply {
                    put("id", expense.id)
                    put("amount", expense.amount)
                    put("type", expense.type)
                    put("category", expense.category)
                    put("date", expense.date)
                    put("comment", expense.comment)
                }
                jsonArray.put(jsonObject)
            }
            
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                it.write(jsonArray.toString().toByteArray())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun loadExpenses(): List<Expense> {
        val expenses = mutableListOf<Expense>()
        val file = File(context.filesDir, fileName)
        
        if (!file.exists()) return emptyList()

        try {
            val content = context.openFileInput(fileName).bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(content)
            
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                expenses.add(
                    Expense(
                        id = obj.optInt("id", 0),
                        amount = obj.getDouble("amount"),
                        type = obj.getString("type"),
                        category = obj.getString("category"),
                        date = obj.getLong("date"),
                        comment = obj.optString("comment", "")
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return expenses
    }
}
