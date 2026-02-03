package com.example.expensetracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.SimpleSettingsStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val storage: SimpleSettingsStorage) : ViewModel() {

    private val _isDarkTheme = MutableStateFlow(true)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            _isDarkTheme.value = storage.isDarkTheme()
        }
    }

    fun toggleTheme() {
        val newSetting = !_isDarkTheme.value
        _isDarkTheme.value = newSetting
        viewModelScope.launch {
            storage.saveTheme(newSetting)
        }
    }
}

class SettingsViewModelFactory(private val storage: SimpleSettingsStorage) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(storage) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
