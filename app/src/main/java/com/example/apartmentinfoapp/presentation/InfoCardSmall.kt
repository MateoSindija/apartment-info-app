package com.example.apartmentinfoapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apartmentinfoapp.R
import com.example.apartmentinfoapp.presentation.ui.theme.Typography
import kotlin.math.roundToInt

fun renderText(type: String, state: WeatherState): String {
    return when(type){
        "Wind" -> "${state.weatherInfo?.currentWeatherData?.windSpeed?.roundToInt()} km/h"
        "Precipitation" -> "${state.weatherInfo?.currentWeatherData?.precipitation} %"
        "Humidity" -> "${state.weatherInfo?.currentWeatherData?.humidity?.roundToInt()} %"
        else -> {
            ""
        }
    }
}

@Composable
fun InfoCardSmall(type: String, drawable: Int, state: WeatherState) {
    val blueColor = colorResource(id = R.color.tufts_blue)
    val blackColor = colorResource(id = R.color.space_cadet)
    // First column
    Box(
        modifier = Modifier
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = Color(0f, 0f, 0f, 0.25f),
                clip = false
            )

    ) {
        Box(
            modifier = Modifier
                .height(105.dp)
                .width(220.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Color.White,
                )

        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = renderText(type, state),
                        color = blueColor,
                        fontWeight = FontWeight(600),
                        style = Typography.bodyLarge.copy(fontSize = 30.sp),

                        )
                    Text(
                        text = type,
                        style = MaterialTheme.typography.labelSmall,
                        color = blackColor,
                        fontWeight = FontWeight(500)
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Icon(
                        painter = painterResource(id = drawable),
                        contentDescription = null, // Set content description as needed
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .fillMaxSize() // Make sure the icon takes the full size of its container
                    )
                }
            }
        }
    }
}