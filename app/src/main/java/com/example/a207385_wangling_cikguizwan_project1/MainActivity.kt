package com.example.a207385_wangling_cikguizwan_project1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import androidx.navigation.NavController
import com.example.a207385_wangling_cikguizwan_project1.ui.theme.GroceryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroceryTheme {

                val navController = rememberNavController()
                val viewModel: GroceryViewModel = viewModel()

                NavHost(navController, startDestination = "home") {

                    composable("home") {
                        HomeScreen(navController)
                    }

                    composable("form") {
                        FormScreen(viewModel) {
                            viewModel.selectItem(it)
                            navController.navigate("detail")
                        }
                    }

                    composable("detail") {
                        DetailScreen(viewModel) {
                            viewModel.selectedItem?.let {
                                viewModel.addIngredient(it)
                            }
                            navController.navigate("summary")
                        }
                    }

                    composable("summary") {
                        SummaryScreen(viewModel,
                            onCalc = { navController.navigate("calc") },
                            onBack = { navController.navigate("form") }
                        )
                    }

                    composable("calc") {
                        CalculationScreen(viewModel) {
                            viewModel.resetProjectData()
                            navController.navigate("home") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp, vertical = 40.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "SDG 12: Responsible Consumption",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E7D32),
            textAlign = TextAlign.Center,
            lineHeight = 38.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Food waste is a major global issue.\n" +
                    "Many households forget what they already have,\n" +
                    "leading to overbuying and waste.",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.DarkGray,
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.height(20.dp))


        Text(
            text = "This app helps users track their food items\n" +
                    "and reduce unnecessary waste 🌱",
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF757575),
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(48.dp))


        Button(
            onClick = { navController.navigate("form") },
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEF6C00)
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                "Start Adding Items",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(20.dp))


        OutlinedButton(
            onClick = { navController.navigate("summary") },
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                "View Fridge List",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
@Composable
fun FormScreen(viewModel: GroceryViewModel, onClick: (GroceryItem) -> Unit) {

    // 👉 按 category 分组
    val groupedItems = viewModel.allIngredients.groupBy { it.category }

    LazyColumn(modifier = Modifier.padding(16.dp)) {

        groupedItems.forEach { (category, items) ->

            // 🟢 分类标题
            item {
                Text(
                    text = category,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // 🟢 分类里的每个 item
            items(items) { item ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClick(item) }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = painterResource(item.imageRes),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(item.name, fontWeight = FontWeight.Bold)
                        Text(item.category, fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailScreen(viewModel: GroceryViewModel, onAdd: () -> Unit) {
    val item = viewModel.selectedItem
    if (item == null) {
        Text("No item selected", color = Color.Red)
        return
    }

    item?.let {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painterResource(it.imageRes), null, modifier = Modifier.size(200.dp))
            Text(it.name, fontSize = 24.sp)

            Text("${item.impactValue} kg CO2", fontSize = 12.sp, color = Color.Gray)
            Button(onClick = onAdd) {
                Text("Add")
            }
        }
    }
}
@Composable
fun SummaryScreen(viewModel: GroceryViewModel, onCalc: () -> Unit, onBack: () -> Unit) {

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Summary", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        if (viewModel.summaryItems.isEmpty()) {
            Text("No items yet", color = Color.Gray)
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(viewModel.summaryItems) { item ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = painterResource(item.imageRes),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(item.name, fontWeight = FontWeight.Bold)
                        Text(item.category, color = Color.Gray, fontSize = 12.sp)
                        Text("${item.impactValue} kg CO2", fontSize = 12.sp, color = Color.Gray)
                    }

                    IconButton(onClick = { viewModel.removeIngredient(item) }) {
                        Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                    }
                }
            }
        }

        Row {
            Button(onClick = onBack, modifier = Modifier.weight(1f)) {
                Text("Back")
            }
            Button(onClick = onCalc, modifier = Modifier.weight(1f)) {
                Text("Calculate")
            }
        }
    }
}
@Composable
fun CalculationScreen(viewModel: GroceryViewModel, onFinish: () -> Unit) {
    val totalImpact = viewModel.summaryItems.sumOf { it.impactValue }
    val count = viewModel.summaryItems.size

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Final Result",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E7D32)
        )

        Spacer(Modifier.height(24.dp))

        Text("Items: $count", fontSize = 24.sp)
        Text(
            "CO₂ Reduction: ${String.format("%.2f", totalImpact)} kg",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(24.dp))

        Text(
            "Reduce waste • Protect environment 🌍",
            color = Color.Gray,
            fontSize = 20.sp
        )

        Spacer(Modifier.height(40.dp))

        Button(
            onClick = onFinish,
            modifier = Modifier.size(width = 160.dp, height = 50.dp)
        ) {
            Text("Finish")
        }
    }
}