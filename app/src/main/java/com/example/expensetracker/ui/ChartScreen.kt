package com.example.expensetracker.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.ui.theme.Black
import com.example.expensetracker.ui.theme.PurplePrimary
import com.example.expensetracker.ui.theme.White

@Composable
fun ChartScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Expense Breakdown", color = White, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(32.dp))
        
        // Simple Placeholder Chart using Canvas
        Canvas(modifier = Modifier.size(200.dp)) {
            val radius = size.minDimension / 2
            val center = Offset(size.width / 2, size.height / 2)
            
            // Draw a simple circle to represent the chart for now
            drawCircle(
                color = PurplePrimary,
                radius = radius,
                style = Stroke(width = 40f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Chart Coming Soon", color = Color.Gray)
    }
}
