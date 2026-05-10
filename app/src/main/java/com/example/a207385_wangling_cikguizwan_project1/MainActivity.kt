package com.example.a207385_wangling_cikguizwan_project1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.a207385_wangling_cikguizwan_project1.ui.theme.GroceryTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroceryTheme {
                val navController = rememberNavController()
                val viewModel: GroceryViewModel = viewModel()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    bottomBar = {
                        if (currentRoute in listOf("home", "form", "summary")) {
                            NavigationBar(
                                containerColor = Color.White,
                                tonalElevation = 8.dp
                            ) {
                                NavigationBarItem(
                                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                                    label = { Text("Home") },
                                    selected = currentRoute == "home",
                                    onClick = {
                                        navController.navigate("home") {
                                            popUpTo("home") { inclusive = true }
                                            launchSingleTop = true
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = Color(0xFF2E7D32),
                                        indicatorColor = Color(0xFFC8E6C9)
                                    )
                                )
                                NavigationBarItem(
                                    icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
                                    label = { Text("Add Item") },
                                    selected = currentRoute == "form",
                                    onClick = {
                                        navController.navigate("form") {
                                            popUpTo("home")
                                            launchSingleTop = true
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = Color(0xFF2E7D32),
                                        indicatorColor = Color(0xFFC8E6C9)
                                    )
                                )
                                NavigationBarItem(
                                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "List") },
                                    label = { Text("My Fridge") },
                                    selected = currentRoute == "summary",
                                    onClick = {
                                        navController.navigate("summary") {
                                            popUpTo("home")
                                            launchSingleTop = true
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = Color(0xFF2E7D32),
                                        indicatorColor = Color(0xFFC8E6C9)
                                    )
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
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
                                onCalc = { navController.navigate("calc") }
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
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Eco Fridge Tracker",
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF1B5E20),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "SDG 12: Responsible Consumption",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF4CAF50),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🌍",
                    fontSize = 56.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Food waste is a major global issue that directly increases our carbon footprint.",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 24.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Track your fridge items and calculate the CO₂ you save by consuming responsibly.",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF757575),
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                navController.navigate("form") {
                    popUpTo("home")
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF6C00)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Start Tracking Now", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun FormScreen(viewModel: GroceryViewModel, onClick: (GroceryItem) -> Unit) {
    val groupedItems = viewModel.allIngredients.groupBy { it.category }
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        groupedItems.forEach { (category, items) ->
            item {
                Text(
                    text = category,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
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
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painterResource(item.imageRes), null, modifier = Modifier.size(200.dp))
        Spacer(Modifier.height(20.dp))
        Text(item.name, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Carbon Footprint Saved: ${item.impactValue} kg CO₂", fontSize = 16.sp, color = Color.Gray)
        Spacer(Modifier.height(30.dp))
        Button(
            onClick = onAdd,
            modifier = Modifier.size(width = 160.dp, height = 50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
        ) {
            Text("Add to Fridge", fontSize = 18.sp)
        }
    }
}

@Composable
fun SummaryScreen(viewModel: GroceryViewModel, onCalc: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("My Fridge 🥦", fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))

        if (viewModel.summaryItems.isEmpty()) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(
                    text = "Your fridge is empty! 🌬️\nLet's add some food to start saving the planet.",
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(viewModel.summaryItems) { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
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
                            Text("${item.impactValue} kg CO₂", fontSize = 12.sp, color = Color.Gray)
                        }
                        IconButton(onClick = { viewModel.removeIngredient(item) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(10.dp))
        Button(
            onClick = onCalc,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            enabled = viewModel.summaryItems.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF6C00))
        ) {
            Text("Calculate CO₂ Saved", fontSize = 18.sp)
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
        Text("Your Impact", fontSize = 40.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
        Spacer(Modifier.height(24.dp))
        Text("Items Saved from Waste: $count", fontSize = 20.sp)
        Text(
            "CO₂ Reduction: ${String.format(Locale.US, "%.2f", totalImpact)} kg",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(24.dp))
        Text("Reduce waste • Protect environment 🌍", color = Color.Gray, fontSize = 18.sp)
        Spacer(Modifier.height(40.dp))
        Button(
            onClick = onFinish,
            modifier = Modifier.size(width = 160.dp, height = 50.dp)
        ) {
            Text("Finish")
        }
    }
}