package com.example.expensetracker.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.data.Expense
import com.example.expensetracker.ui.theme.*

@Composable
fun DonutChart(
    expenses: List<Expense>,
    textColor: Color,
    modifier: Modifier = Modifier,
    chartSize: Dp = 180.dp,
    strokeWidth: Dp = 25.dp
) {
    val total = expenses.filter { it.type == "Expense" }.sumOf { it.amount }
    val categoryTotals = expenses
        .filter { it.type == "Expense" }
        .groupBy { it.category }
        .mapValues { it.value.sumOf { exp -> exp.amount } }
        .filter { it.value > 0 } // Only show categories with spending

    // Colors mapping
    val categoryColors = mapOf(
        "Food" to NeonPurple,
        "Travel" to NeonCyan,
        "Shopping" to Color(0xFFFF4081),
        "Bills" to Color(0xFFFFD700),
        "Health" to Color(0xFF4CAF50),
        "Other" to Color.Gray
    )

    // Animation trigger
    val animationProgress = remember { Animatable(0f) }
    LaunchedEffect(expenses) {
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 800, easing = LinearEasing)
        )
    }

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        // 1. Chart
        Box(
            modifier = Modifier.size(chartSize),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = (size.minDimension - strokeWidth.toPx()) / 2
                
                var startAngle = -90f

                if (total == 0.0) {
                     drawCircle(
                        color = Color.LightGray.copy(alpha = 0.2f),
                        radius = radius,
                        style = Stroke(width = strokeWidth.toPx())
                    )
                } else {
                    categoryTotals.forEach { (category, amount) ->
                        val sweepAngle = (amount / total * 360f).toFloat() * animationProgress.value
                        // Add a small gap between segments
                        val gapAngle = 2f 
                        val drawSweep = if (sweepAngle > gapAngle) sweepAngle - gapAngle else sweepAngle
                        
                        drawArc(
                            color = categoryColors[category] ?: Color.LightGray,
                            startAngle = startAngle,
                            sweepAngle = drawSweep,
                            useCenter = false,
                            topLeft = Offset(center.x - radius, center.y - radius),
                            size = Size(radius * 2, radius * 2),
                            style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
                        )
                        
                        startAngle += sweepAngle
                    }
                }
            }
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                 Text(
                    text = "Total",
                    color = textColor.copy(alpha = 0.6f),
                    fontSize = 12.sp
                )
                Text(
                    text = "${total.toInt()}",
                    color = textColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.width(24.dp))

        // 2. Legend
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            categoryTotals.keys.forEach { category ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(categoryColors[category] ?: Color.Gray)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = category,
                        color = textColor.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
