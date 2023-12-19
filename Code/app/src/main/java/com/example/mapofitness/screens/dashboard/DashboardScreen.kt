package com.example.mapofitness.screens.dashboard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mapofitness.theme.DarkGray

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val bodyFat by viewModel.bodyFat.collectAsState()
    val bmi by viewModel.bmi.collectAsState()
    val metabolism by viewModel.metabolism.collectAsState()

    viewModel.updateBmi()
    viewModel.updateBodyFat()
    viewModel.updateMetabolism()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        DashboardItem(title = "Body Fat", value = String.format("%.2f%%", bodyFat))
        DashboardItem(title = "BMI", value = String.format("%.1f", bmi))
        DashboardItem(title = "Metabolism", value = String.format("%.0f kcal/day", metabolism))
    }
}

@Composable
fun DashboardItem(title: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = DarkGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(text = value, style = MaterialTheme.typography.bodyLarge)
        }
    }
}