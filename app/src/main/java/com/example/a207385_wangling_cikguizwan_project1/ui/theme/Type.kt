package com.example.a207385_wangling_cikguizwan_project1.ui.theme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color

val AppTypography = Typography(

    // 1. headlineLarge: 用在 App 最顶部的主标题
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 34.sp,
        letterSpacing = 1.sp,
        color = Color(0xFFFF6D00)
    ),

    // 2. titleLarge: 用在分类大标题
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = 0.5.sp
    ),

    // 3. titleMedium: 用在每一个食材的名字
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        color = Color(0xFF2C2C2C)
    ),

    // 4. bodyLarge: 用在普通的正文描述
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        color = Color.DarkGray,
        lineHeight = 20.sp
    ),
    // 5. labelLarge: 专门负责按钮 (Button) 上的文字样式
    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        letterSpacing = 0.sp
    )
)
