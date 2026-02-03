package com.example.expensetracker.data

import android.content.Context
import org.json.JSONObject
import java.io.File

class SimpleSettingsStorage(private val context: Context) {
    private val fileName = "settings.json"

    fun saveTheme(isDark: Boolean) {
        try {
            val jsonObject = JSONObject().apply {
                put("is_dark_theme", isDark)
            }
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                it.write(jsonObject.toString().toByteArray())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isDarkTheme(): Boolean {
        val file = File(context.filesDir, fileName)
        if (!file.exists()) return true // Default to Dark

        return try {
            val content = context.openFileInput(fileName).bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(content)
            jsonObject.optBoolean("is_dark_theme", true)
        } catch (e: Exception) {
            true
        }
    }
}
