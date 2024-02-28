package com.example.apartmentinfoapp.presentation.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.apartmentinfoapp.R


private val customFontFamily = FontFamily(
    Font(R.font.golos_regular, FontWeight.W400),
    Font(R.font.golos_bold, FontWeight.W700),
    Font(R.font.golos_medium, FontWeight.W500),
    Font(R.font.golos_semi_bold, FontWeight.W600),
    // Add more font weights or styles if needed
)

// Set of Material typography styles to start with
@SuppressLint("ResourceAsColor")
val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        color = Color(R.color.space_cadet)
    ),
    bodyLarge = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        color = Color(R.color.space_cadet)
    ),

    titleLarge = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = Color(R.color.space_cadet)
    ),
)