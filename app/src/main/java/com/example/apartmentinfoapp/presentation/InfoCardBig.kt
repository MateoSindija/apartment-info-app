package com.example.apartmentinfoapp.presentation

import android.icu.text.ListFormatter.Width
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apartmentinfoapp.R
import com.example.apartmentinfoapp.presentation.ui.theme.Typography
import java.time.format.DateTimeFormatter

fun degToCompass(num: Int): String {
    val angle = ((num / 22.5) + .5).toInt()
    val arr = arrayOf(
        "N",
        "NNE",
        "NE",
        "ENE",
        "E",
        "ESE",
        "SE",
        "SSE",
        "S",
        "SSW",
        "SW",
        "WSW",
        "W",
        "WNW",
        "NW",
        "NNW"
    )
    return arr[(angle % 16)]
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InfoCardBig(state: WeatherState) {
    val blackColor = colorResource(id = R.color.space_cadet)

    val todayDate by mutableStateOf(
        state.weatherInfo?.currentWeatherData?.time?.format(DateTimeFormatter.ofPattern("EEEE, MMMM d"))
            ?: ""
    )
    val temperature by mutableStateOf(
        state.weatherInfo?.currentWeatherData?.temperatureCelsius?.toInt()
            ?: ""
    )

    val weatherState by mutableStateOf(
        state.weatherInfo?.currentWeatherData?.weatherType?.weatherDesc ?: ""
    )

    val pressure by mutableStateOf(state.weatherInfo?.currentWeatherData?.pressure?.toInt() ?: 0)
    val windDirection by mutableStateOf(state.weatherInfo?.currentWeatherData?.windDirection ?: 0)
    val visibility by mutableStateOf(
        state.weatherInfo?.currentWeatherData?.visibility?.toInt() ?: 0
    )

    val sunrise by mutableStateOf(
        state.weatherInfo?.currentDayLengthData?.data?.sunrise?.format(
            DateTimeFormatter.ofPattern("HH:mm")
        ) ?: ""
    )
    val sunset by mutableStateOf(
        state.weatherInfo?.currentDayLengthData?.data?.sunset?.format(
            DateTimeFormatter.ofPattern("HH:mm")
        ) ?: ""
    )

    Box(
        modifier = Modifier
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = Color(0f, 0f, 0f, 0.25f),
                clip = true
            )

    ) {
        Box(
            modifier = Modifier
                .background(
                    Color.White,
                )
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = todayDate,
                        style = Typography.bodyLarge.copy(fontSize = 13.sp),
                        color = blackColor,
                        fontWeight = FontWeight(500)
                    )
                    Spacer(modifier = Modifier.width(40.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_sunrise),
                        contentDescription = null,
                        Modifier.height(14.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = sunrise,
                        style = Typography.bodyLarge.copy(fontSize = 12.sp),
                        color = blackColor,
                        fontWeight = FontWeight(500)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_night_moon),
                        contentDescription = null,
                        Modifier.height(14.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = sunset,
                        style = Typography.bodyLarge.copy(fontSize = 12.sp),
                        color = blackColor,
                        fontWeight = FontWeight(500)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Image(
                            painter = painterResource(
                                id = state.weatherInfo?.currentWeatherData?.weatherType?.iconRes
                                    ?: R.drawable.ic_cloudy
                            ),
                            contentDescription = null,
                            modifier = Modifier.height(60.dp)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 20.dp),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = "$temperature °C",
                            style = Typography.bodyLarge.copy(fontSize = 38.sp),
                            color = blackColor,
                            fontWeight = FontWeight(500)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(start = 20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Pressure: $pressure hPa",
                            style = Typography.bodyLarge.copy(fontSize = 12.sp),
                            color = blackColor,
                        )
                        Text(
                            text = "Visibility: $visibility m",
                            style = Typography.bodyLarge.copy(fontSize = 12.sp),
                            color = blackColor,
                        )
                        Text(
                            text = "Wind direction: ${degToCompass(windDirection)}",
                            style = Typography.bodyLarge.copy(fontSize = 12.sp),
                            color = blackColor,
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = weatherState,
                        style = Typography.bodyLarge.copy(fontSize = 24.sp),
                        color = blackColor,
                        fontWeight = FontWeight(500)
                    )
                }
            }
        }
    }

}

