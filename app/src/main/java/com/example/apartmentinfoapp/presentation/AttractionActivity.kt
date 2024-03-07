package com.example.apartmentinfoapp.presentation

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.apartmentinfoapp.BuildConfig
import com.example.apartmentinfoapp.R
import com.example.apartmentinfoapp.presentation.composables.imageUrl
import com.example.apartmentinfoapp.presentation.composables.title
import com.example.apartmentinfoapp.presentation.models.CommonCardData
import com.example.apartmentinfoapp.presentation.ui.theme.ApartmentInfoAppTheme
import com.example.apartmentinfoapp.presentation.ui.theme.Typography
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.type.LatLng


class AttractionActivity : ComponentActivity() {
    @OptIn(ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        val commonCardData = intent.getParcelableExtra<CommonCardData>("data")

        if (commonCardData !== null) {
            setContent {
                var expandedImageUrl by remember { mutableStateOf<String?>(null) }
                var componentState by remember { mutableStateOf("photos") }
                val imagesUrls = when (commonCardData) {
                    is CommonCardData.RestaurantCard -> commonCardData.restaurantData?.imagesUrl
                        ?: listOf("")

                    is CommonCardData.BeachCard -> commonCardData.beachData?.imagesUrl ?: listOf("")
                }
                val lat = when (commonCardData) {
                    is CommonCardData.RestaurantCard -> {
                        commonCardData.restaurantData?.lat ?: 0.0
                    }

                    is CommonCardData.BeachCard -> {
                        commonCardData.beachData?.lat ?: 0.0
                    }
                }
                val lng = when (commonCardData) {
                    is CommonCardData.RestaurantCard -> {
                        commonCardData.restaurantData?.lng ?: 0.0
                    }

                    is CommonCardData.BeachCard -> {
                        commonCardData.beachData?.lng ?: 0.0
                    }
                }
                val attractionLocation =
                    com.google.android.gms.maps.model.LatLng(lat, lng)
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(attractionLocation, 10f)
                }
                ApartmentInfoAppTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color(0xFFF2F4F5)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(bottom = 30.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current).data(
                                        imageUrl(commonCardData)
                                    ).crossfade(true).build(),
                                    placeholder = painterResource(id = R.drawable.ic_image_loading),
                                    contentDescription = "Beach Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize(),
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Transparent)
                                        .padding(start = 85.dp, bottom = 30.dp),
                                    contentAlignment = Alignment.BottomStart
                                ) {
                                    Column {
                                        Text(
                                            text = title(
                                                commonCardData
                                            ),
                                            style = Typography.bodyLarge,
                                            color = colorResource(id = R.color.white),
                                            fontWeight = FontWeight.W500,
                                            fontSize = 54.sp
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        RenderSubHeader(commonCardData)

                                    }
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 85.dp, end = 85.dp, top = 29.dp)
                            ) {
                                Text(
                                    text = "About", color = colorResource(id = R.color.space_cadet),
                                    fontWeight = FontWeight.W500,
                                    fontSize = 24.sp
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                AboutText(commonCardData)

                                Row(modifier = Modifier.padding(top = 50.dp, bottom = 30.dp)) {
                                    TextButton(onClick = { componentState = "photos" }) {
                                        Text(
                                            text = "Photos",
                                            color = colorResource(id = R.color.space_cadet),
                                            fontWeight = FontWeight.W500,
                                            fontSize = 20.sp
                                        )
                                    }
                                    if (commonCardData is CommonCardData.RestaurantCard) {
                                        TextButton(onClick = { componentState = "contacts" }) {
                                            Text(
                                                text = "Contacts",
                                                color = colorResource(id = R.color.space_cadet),
                                                fontWeight = FontWeight.W500,
                                                fontSize = 20.sp
                                            )

                                        }
                                    }
                                    TextButton(onClick = { componentState = "location" }) {
                                        Text(
                                            text = "Location",
                                            color = colorResource(id = R.color.space_cadet),
                                            fontWeight = FontWeight.W500,
                                            fontSize = 20.sp
                                        )
                                    }
                                }
                                if (componentState == "photos") {
                                    FlowRow(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                    ) {
                                        repeat(imagesUrls.size) { index ->
                                            AsyncImage(
                                                model = ImageRequest.Builder(LocalContext.current)
                                                    .data(
                                                        imagesUrls[index]
                                                    ).crossfade(true).build(),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .padding(5.dp)
                                                    .height(200.dp)
                                                    .aspectRatio(1f)
                                                    .clip(MaterialTheme.shapes.medium)
                                                    .clickable {
                                                        expandedImageUrl = imagesUrls[index]
                                                    },
                                                contentScale = ContentScale.Crop,
                                            )

                                        }
                                    }
                                    expandedImageUrl?.let { imageUrl ->
                                        FullScreenImageDialog(
                                            imageUrl = imageUrl,
                                            onClose = { expandedImageUrl = null }
                                        )
                                    }
                                } else if (componentState == "contacts" && commonCardData is CommonCardData.RestaurantCard) {
                                    Column() {
                                        Row() {
                                            Image(
                                                painter = painterResource(id = R.drawable.ic_phone_book),
                                                contentDescription = "phone book",
                                                modifier = Modifier
                                                    .width(25.dp)
                                                    .height(25.dp)
                                            )
                                            Spacer(modifier = Modifier.width(20.dp))
                                            Text(
                                                text = commonCardData.restaurantData?.contacts?.number
                                                    ?: "",
                                                color = colorResource(id = R.color.space_cadet),
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(30.dp))
                                        Row() {
                                            Image(
                                                painter = painterResource(id = R.drawable.ic_mail),
                                                contentDescription = "mail",
                                                modifier = Modifier
                                                    .width(25.dp)
                                                    .height(25.dp)

                                            )
                                            Spacer(modifier = Modifier.width(20.dp))
                                            Text(
                                                text = commonCardData.restaurantData?.contacts?.email
                                                    ?: "",
                                                color = colorResource(id = R.color.space_cadet),
                                            )
                                        }
                                    }
                                } else {
                                    GoogleMap(
                                        modifier = Modifier.fillMaxWidth().height(500.dp),
                                        cameraPositionState = cameraPositionState
                                    ) {
                                        Marker(
                                            state = MarkerState(position = attractionLocation)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun FullScreenImageDialog(imageUrl: String, onClose: () -> Unit) {

        Dialog(onClose) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 10.dp)
                    .background(Color.Transparent)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl).crossfade(true).build(),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()

                )
            }
        }
    }

    @Composable
    fun RenderSubHeader(commonCardData: CommonCardData) {
        Row {
            return when (commonCardData) {
                is CommonCardData.RestaurantCard -> {
                    Image(
                        painter = painterResource(
                            id = R.drawable.ic_star,
                        ), contentDescription = "star",
                        modifier = Modifier.height(16.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "${commonCardData.restaurantData?.review}",
                        style = Typography.bodyMedium,
                        color = colorResource(id = R.color.yellow_crayola),
                        fontWeight = FontWeight(500),
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "(${commonCardData.restaurantData?.reviewAmount} reviews)",
                        style = Typography.bodyMedium,
                        color = colorResource(id = R.color.light_gray),
                        fontWeight = FontWeight(500),
                    )
                }

                is CommonCardData.BeachCard -> {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(
                                id = commonCardData.beachData?.weatherData?.weatherType?.iconRes
                                    ?: R.drawable.ic_cloudy,
                            ), contentDescription = "weather",
                            modifier = Modifier.height(16.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${commonCardData.beachData?.weatherData?.temperatureCelsius} °C",
                            style = Typography.bodyMedium,
                            color = colorResource(id = R.color.light_gray),
                            fontWeight = FontWeight(500)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ic_vertical_line),
                            contentDescription = "line",
                            modifier = Modifier.height(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = commonCardData.beachData?.weatherData?.weatherType?.weatherDesc
                                ?: "",
                            style = Typography.bodyMedium,
                            color = colorResource(id = R.color.light_gray),
                            fontWeight = FontWeight(500)
                        )
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_mountain),
                            contentDescription = "sand",
                            modifier = Modifier.height(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = (commonCardData.beachData?.terrainType
                                ?: "Sand").replaceFirstChar { it.uppercase() },
                            style = Typography.bodyMedium,
                            color = colorResource(id = R.color.light_gray),
                            fontWeight = FontWeight(500)
                        )
                    }
                }
            }
        }
    }


    @Composable
    fun AboutText(commonCardData: CommonCardData) {
        Text(
            text = when (commonCardData) {
                is CommonCardData.BeachCard -> {
                    commonCardData.beachData?.description ?: ""
                }

                is CommonCardData.RestaurantCard -> {
                    commonCardData.restaurantData?.description ?: ""
                }
            },
            color = colorResource(id = R.color.davy_grey),
            fontWeight = FontWeight(500),
            fontSize = 15.sp
        )
    }

}

