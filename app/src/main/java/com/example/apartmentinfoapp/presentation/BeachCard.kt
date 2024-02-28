package com.example.apartmentinfoapp.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apartmentinfoapp.domain.beaches.BeachData
import com.example.apartmentinfoapp.presentation.ui.theme.ApartmentInfoAppTheme

@Composable
fun BeachCard(beachData: BeachData?) {
    Card(
        modifier = Modifier
            .fillMaxHeight()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = Color(0f, 0f, 0f, 0.25f),
                clip = true
            )
    ) {
        Text(text = beachData?.title ?: "")
    }
}

