package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.data.SimpleExpenseStorage
import com.example.expensetracker.data.SimpleSettingsStorage
import com.example.expensetracker.ui.AddExpenseScreen
import com.example.expensetracker.ui.ChartScreen
import com.example.expensetracker.ui.ExpenseViewModel
import com.example.expensetracker.ui.ExpenseViewModelFactory
import com.example.expensetracker.ui.HomeScreen
import com.example.expensetracker.ui.SettingsViewModel
import com.example.expensetracker.ui.SettingsViewModelFactory
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize Storage
        val expenseStorage = SimpleExpenseStorage(this)
        val settingsStorage = SimpleSettingsStorage(this)
        
        // ViewModels
        val expenseViewModelFactory = ExpenseViewModelFactory(expenseStorage)
        val expenseViewModel = ViewModelProvider(this, expenseViewModelFactory)[ExpenseViewModel::class.java]
        
        val settingsViewModelFactory = SettingsViewModelFactory(settingsStorage)
        val settingsViewModel = ViewModelProvider(this, settingsViewModelFactory)[SettingsViewModel::class.java]

        setContent {
            val isDarkTheme by settingsViewModel.isDarkTheme.collectAsState()
            
            ExpenseTrackerTheme(darkTheme = isDarkTheme) {
                // Surface ensures we have a valid background even if Scaffold padding fails
                androidx.compose.material3.Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            val expenses by expenseViewModel.allExpenses.collectAsState()
                            val monthlyTotal by expenseViewModel.monthlyTotal.collectAsState()

                            HomeScreen(
                                expenses = expenses,
                                monthlyTotal = monthlyTotal ?: 0.0,
                                isDarkTheme = isDarkTheme,
                                onThemeToggle = { settingsViewModel.toggleTheme() },
                                onAddClick = { navController.navigate("add_expense") },
                                onDeleteClick = { expenseViewModel.deleteExpense(it) }
                            )
                        }
                        composable("add_expense") {
                            AddExpenseScreen(
                                onSaveClick = { expense ->
                                    expenseViewModel.addExpense(expense)
                                    navController.popBackStack()
                                },
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                        composable("chart") {
                            ChartScreen()
                        }
                    }
                }
            }
        }
    }
}