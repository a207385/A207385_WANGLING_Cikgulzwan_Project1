package com.example.a207385_wangling_cikguizwan_project1.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val WarmYellowColorScheme = lightColorScheme(
    primary = OrangePrimary,
    secondary = WarmYellowSecondary,
    background = AppBackground,
    surface = CardSurface,

    onPrimary = Color.Black,      // 主色按钮（Search）上的字
    onSecondary = Color.Black,    // 次色按钮（View Cart）上的字
    onBackground = Color.Black,   // 页面背景上的字
    onSurface = Color.Black       // 卡片上的字
)

@Composable
fun GroceryTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = WarmYellowColorScheme,
        typography = AppTypography,
        shapes = Shapes(
            medium = RoundedCornerShape(size = 36.dp)
        ),
        content = content
    )
}