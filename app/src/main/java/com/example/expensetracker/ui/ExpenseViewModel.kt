package com.example.expensetracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.Expense
import com.example.expensetracker.data.SimpleExpenseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExpenseViewModel(private val storage: SimpleExpenseStorage) : ViewModel() {

    private val _allExpenses = MutableStateFlow<List<Expense>>(emptyList())
    val allExpenses: StateFlow<List<Expense>> = _allExpenses.asStateFlow()

    private val _monthlyTotal = MutableStateFlow<Double>(0.0)
    val monthlyTotal: StateFlow<Double?> = _monthlyTotal.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val list = storage.loadExpenses().sortedByDescending { it.date }
            _allExpenses.value = list
            calculateTotal(list)
        }
    }

    private fun calculateTotal(list: List<Expense>) {
        val total = list.filter { it.type == "Expense" }.sumOf { it.amount }
        _monthlyTotal.value = total
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            val currentList = _allExpenses.value.toMutableList()
            currentList.add(0, expense) // Add to top
            _allExpenses.value = currentList
            calculateTotal(currentList)
            storage.saveExpenses(currentList)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            val currentList = _allExpenses.value.toMutableList()
            currentList.remove(expense)
            _allExpenses.value = currentList
            calculateTotal(currentList)
            storage.saveExpenses(currentList)
        }
    }
}

class ExpenseViewModelFactory(private val storage: SimpleExpenseStorage) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpenseViewModel(storage) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
