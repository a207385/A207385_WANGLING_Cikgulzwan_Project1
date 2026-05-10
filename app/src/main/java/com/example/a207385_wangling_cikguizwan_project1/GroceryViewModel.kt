package com.example.a207385_wangling_cikguizwan_project1

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class GroceryViewModel : ViewModel() {

    val allIngredients = listOf(
        GroceryItem("Onion", R.drawable.onion, "Health Choices", 0.3),
        GroceryItem("Cucumber", R.drawable.cucumber, "Health Choices", 0.2),
        GroceryItem("Spinach", R.drawable.spinach, "Health Choices", 0.25),
        GroceryItem("Garlic", R.drawable.garlic, "Health Choices", 0.15),
        GroceryItem("Ginger", R.drawable.ginger, "Health Choices", 0.2),

        GroceryItem("Tomato", R.drawable.tomato, "Vegetables", 0.4),
        GroceryItem("Carrot", R.drawable.carrot, "Vegetables", 0.35),
        GroceryItem("Broccoli", R.drawable.broccoli, "Vegetables", 0.45),
        GroceryItem("Potato", R.drawable.potato, "Vegetables", 0.5),
        GroceryItem("Corn", R.drawable.corn, "Vegetables", 0.55),

        GroceryItem("Apple", R.drawable.apple, "Fresh Fruits", 0.3),
        GroceryItem("Banana", R.drawable.banana, "Fresh Fruits", 0.25),
        GroceryItem("Orange", R.drawable.orange, "Fresh Fruits", 0.28),
        GroceryItem("Grape", R.drawable.grape, "Fresh Fruits", 0.32),
        GroceryItem("Mango", R.drawable.mango, "Fresh Fruits", 0.4)
    )

    var selectedItem by mutableStateOf<GroceryItem?>(null)

    private val _summaryItems = mutableStateListOf<GroceryItem>()
    val summaryItems: List<GroceryItem> get() = _summaryItems

    fun selectItem(item: GroceryItem) {
        selectedItem = item
    }

    fun addIngredient(item: GroceryItem): Boolean {
        return if (!_summaryItems.contains(item)) {
            _summaryItems.add(item)
            true
        } else {
            false
        }
    }

    fun removeIngredient(item: GroceryItem) {
        _summaryItems.remove(item)
    }

    fun resetProjectData() {
        _summaryItems.clear()
        selectedItem = null
    }
}
