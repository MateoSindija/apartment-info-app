package com.example.apartmentinfoapp.presentation.composables

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
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
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.apartmentinfoapp.R
import com.example.apartmentinfoapp.presentation.AttractionActivity
import com.example.apartmentinfoapp.presentation.distanceBetweenPointsInKm
import com.example.apartmentinfoapp.presentation.models.CommonCardData
import com.example.apartmentinfoapp.presentation.ui.theme.Typography

 fun imageUrl(data: CommonCardData): String {
    return when (data) {
        is CommonCardData.BeachCard -> data.beachData?.imagesUrl?.get(0) ?: ""
        is CommonCardData.RestaurantCard -> data.restaurantData?.imagesUrl?.get(0) ?: ""
    }
}

fun title(data: CommonCardData): String {
    return when (data) {
        is CommonCardData.BeachCard -> data.beachData?.title ?: ""
        is CommonCardData.RestaurantCard -> data.restaurantData?.title ?: ""
    }
}

fun distance(data: CommonCardData): String {
    return when (data) {
        is CommonCardData.BeachCard -> distanceBetweenPointsInKm(
            data.mineLat,
            data.mineLng,
            data.beachData?.lat,
            data.beachData?.lng
        ).toString()

        is CommonCardData.RestaurantCard -> distanceBetweenPointsInKm(
            data.mineLat,
            data.mineLng,
            data.restaurantData?.lat,
            data.restaurantData?.lng
        ).toString()
    }
}

fun launchNextActivity(data: CommonCardData, context: Context) {

    val attractionIntent = Intent(context, AttractionActivity::class.java)
    attractionIntent.putExtra("data", data)
    context.startActivity(attractionIntent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttractionCard(
    data: CommonCardData,
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .height(240.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = Color(0f, 0f, 0f, 0.25f),
                clip = true
            )
            .width(240.dp)
            .background(Color.White)
            .clickable { },
        onClick = { launchNextActivity(data, context) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(
                    imageUrl(data)
                ).crossfade(true).build(),
                placeholder = painterResource(id = R.drawable.ic_image_loading),
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
                    text = title(data), style = Typography.bodyLarge,
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
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = "${
                            distance(data)
                        } km", style = Typography.bodyMedium,
                        color = colorResource(id = R.color.philippine_gray),
                        fontWeight = FontWeight(500)
                    )
                }
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    when (data) {
                        is CommonCardData.BeachCard -> {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(
                                        id = data.beachData?.weatherData?.weatherType?.iconRes
                                            ?: R.drawable.ic_cloudy,
                                    ), contentDescription = "weather",
                                    modifier = Modifier.height(16.dp)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "${data.beachData?.weatherData?.temperatureCelsius} °C",
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
                                    text = (data.beachData?.terrainType
                                        ?: "Sand").replaceFirstChar { it.uppercase() },
                                    style = Typography.bodyMedium,
                                    color = colorResource(id = R.color.philippine_gray),
                                    fontWeight = FontWeight(500)
                                )
                            }
                        }

                        is CommonCardData.RestaurantCard -> {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(
                                        id = R.drawable.ic_star,
                                    ), contentDescription = "star",
                                    modifier = Modifier.height(16.dp)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "${data.restaurantData?.review}",
                                    style = Typography.bodyMedium,
                                    color = colorResource(id = R.color.yellow_crayola),
                                    fontWeight = FontWeight(500),
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = "(${data.restaurantData?.reviewAmount})",
                                    style = Typography.bodyMedium,
                                    color = colorResource(id = R.color.philippine_gray),
                                    fontWeight = FontWeight(500),
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}

