package com.example.apartmentinfoapp.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.apartmentinfoapp.R
import com.example.apartmentinfoapp.domain.beaches.BeachData
import com.example.apartmentinfoapp.domain.util.Resource
import com.example.apartmentinfoapp.domain.weather.WeatherInfo
import com.example.apartmentinfoapp.presentation.ui.theme.Typography


@Composable
fun BeachCard(
    beachData: BeachData?,
    mineLat: Double?,
    mineLng: Double?,
    weatherResult: Resource<List<WeatherInfo>>?,
) {

    Card(
        modifier = Modifier
            .fillMaxHeight()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = Color(0f, 0f, 0f, 0.25f),
                clip = true
            )
            .width(240.dp)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(
                    beachData?.imagesUrl?.get(0) ?: ""
                ).crossfade(true).build(),
                placeholder = painterResource(id = R.drawable.rain_icon),
                contentDescription = "Beach Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.weight(1.8f)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 10.dp)
                    .weight(1.2f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = beachData?.title ?: "", style = Typography.bodyLarge,
                    color = colorResource(id = R.color.space_cadet),
                    fontWeight = FontWeight(500)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.location_pin),
                        contentDescription = "pin",
                        modifier = Modifier.height(18.dp)
                    )
                    Spacer(Modifier.width(3.dp))
                    Text(
                        text = "${
                            distanceBetweenPointsInKm(
                                mineLat,
                                mineLng,
                                beachData?.lat,
                                beachData?.lng
                            )
                        } km", style = Typography.bodyMedium,
                        color = colorResource(id = R.color.philippine_gray),
                        fontWeight = FontWeight(500)
                    )
                }
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(
                                id = beachData?.weatherData?.weatherType?.iconRes
                                    ?: R.drawable.ic_cloudy,
                            ), contentDescription = "weather",
                            modifier = Modifier.height(16.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "${beachData?.weatherData?.temperatureCelsius} °C",
                            style = Typography.bodyMedium,
                            color = colorResource(id = R.color.philippine_gray),
                            fontWeight = FontWeight(500)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_mountain),
                            contentDescription = "sand",
                            modifier = Modifier.height(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = (beachData?.terrainType
                                ?: "Sand").replaceFirstChar { it.uppercase() },
                            style = Typography.bodyMedium,
                            color = colorResource(id = R.color.philippine_gray),
                            fontWeight = FontWeight(500)
                        )
                    }
                }
            }
        }
    }
}

