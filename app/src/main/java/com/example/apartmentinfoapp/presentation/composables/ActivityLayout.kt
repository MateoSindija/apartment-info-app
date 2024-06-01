package com.example.apartmentinfoapp.presentation.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.apartmentinfoapp.presentation.ui.theme.ApartmentInfoAppTheme

@Composable
fun ActivityLayout(content: @Composable() () -> Unit) {
    ApartmentInfoAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF2F4F5)
        ) {
            content()
        }
    }
}