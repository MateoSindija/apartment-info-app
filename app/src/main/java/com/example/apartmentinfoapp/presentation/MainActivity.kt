package com.example.apartmentinfoapp.presentation

import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apartmentinfoapp.R
import com.example.apartmentinfoapp.presentation.ui.theme.ApartmentInfoAppTheme
import com.example.apartmentinfoapp.presentation.ui.theme.Typography
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.apartmentinfoapp.presentation.composables.ActionCard
import com.example.apartmentinfoapp.presentation.composables.CardsList
import com.example.apartmentinfoapp.presentation.composables.DevicesList
import com.example.apartmentinfoapp.presentation.composables.InfoCardBig
import com.example.apartmentinfoapp.presentation.composables.InfoCardSmall
import com.example.apartmentinfoapp.presentation.models.CommonDataState
import com.example.apartmentinfoapp.presentation.viewmodels.BeachViewModel
import com.example.apartmentinfoapp.presentation.viewmodels.DevicesViewModel
import com.example.apartmentinfoapp.presentation.viewmodels.RestaurantViewModel
import com.example.apartmentinfoapp.presentation.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

fun distanceBetweenPointsInKm(
    startLat: Double?,
    startLng: Double?,
    endLat: Double?,
    endLng: Double?
): Int {

    val locationDistanceResult = floatArrayOf(0.0f, 0.0f, 0.0f)
    Location.distanceBetween(
        startLat ?: 0.0,
        startLng ?: 0.0,
        endLat ?: 0.0,
        endLng ?: 0.0,
        locationDistanceResult
    )

    return (locationDistanceResult[0] / 1000).toInt()
}

fun distanceBetweenPointsInM(
    startLat: Double?,
    startLng: Double?,
    endLat: Double?,
    endLng: Double?
): Int {

    val locationDistanceResult = floatArrayOf(0.0f, 0.0f, 0.0f)
    Location.distanceBetween(
        startLat ?: 0.0,
        startLng ?: 0.0,
        endLat ?: 0.0,
        endLng ?: 0.0,
        locationDistanceResult
    )

    return (locationDistanceResult[0]).toInt()
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModelWeather: WeatherViewModel by viewModels()
    private val viewModelBeaches: BeachViewModel by viewModels()
    private val viewModelDevices: DevicesViewModel by viewModels()
    private val viewModelRestaurants: RestaurantViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModelWeather.loadWeatherInfo()
            viewModelBeaches.loadBeachesInfo()
            viewModelDevices.loadDevicesList()
            viewModelRestaurants.loadRestaurantsInfo()
        }
        permissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.INTERNET
            )
        )


        setContent {
            ApartmentInfoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF2F4F5)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp, bottom = 15.dp, top = 15.dp, end = 40.dp)
                            .verticalScroll(rememberScrollState())
                    ) {

                        Row(Modifier.height(210.dp)) {
                            Column(modifier = Modifier.weight(2f)) {
                                Row(
                                    modifier = Modifier
                                        .height(80.dp)
                                        .width(300.dp)
                                ) {
                                    Text(
                                        text = "Hello George",
                                        style = Typography.displayMedium.copy(
                                            fontSize = 34.sp,
                                            fontWeight = FontWeight(600)
                                        ),
                                        color = colorResource(id = R.color.space_cadet),
                                    )

                                }
                                Text(
                                    text = "Climate",
                                    style = Typography.bodyLarge,
                                    color = colorResource(id = R.color.space_cadet),
                                    fontWeight = FontWeight(500)
                                )
                                Spacer(modifier = Modifier.height(15.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    InfoCardSmall(
                                        type = "Humidity",
                                        R.drawable.water_drop,
                                        viewModelWeather.state
                                    )
                                    InfoCardSmall(
                                        state = viewModelWeather.state,
                                        drawable = R.drawable.wind_icon_com,
                                        type = "Wind",
                                    )
                                    InfoCardSmall(
                                        type = "Precipitation",
                                        drawable = R.drawable.rain_icon,
                                        state = viewModelWeather.state
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .weight(1.3f)
                                    .padding(start = 35.dp)
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                InfoCardBig(viewModelWeather.state)
                            }
                        }
                        Spacer(modifier = Modifier.height(40.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(600.dp),
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(4f)
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = "Beaches (${viewModelBeaches.state.beachInfo?.size})",
                                        style = Typography.bodyLarge,
                                        color = colorResource(id = R.color.space_cadet),
                                        fontWeight = FontWeight(500)
                                    )
                                    Spacer(modifier = Modifier.height(15.dp))
                                    CardsList(CommonDataState.BeachCardList(viewModelBeaches.state))
                                }

                                Column {
                                    Text(
                                        text = "Restaurants (${viewModelRestaurants.restaurantListState.restaurantsInfoList?.size})",
                                        style = Typography.bodyLarge,
                                        color = colorResource(id = R.color.space_cadet),
                                        fontWeight = FontWeight(500)
                                    )
                                    Spacer(modifier = Modifier.height(15.dp))
                                    CardsList(
                                        CommonDataState.RestaurantCardList(
                                            viewModelRestaurants.restaurantListState
                                        )
                                    )

                                }
                            }
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 20.dp)
                                    .fillMaxHeight()

                            ) {
                                Text(
                                    text = "Devices (${viewModelDevices.devicesListState.devicesInfoList?.size})",
                                    style = Typography.bodyLarge,
                                    color = colorResource(id = R.color.space_cadet),
                                    fontWeight = FontWeight(500)
                                )
                                Spacer(modifier = Modifier.height(15.dp))
                                DevicesList(
                                    viewModelDevices.devicesListState,
                                    Modifier.height(600.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(40.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            ActionCard(
                                title = "Contact us",
                                description = "Contact us directly",
                                imageId = R.drawable.ic_chat_ipad,
                                btnText = "Contact us"
                            ) { }
                            ActionCard(
                                title = "Guestbook",
                                description = "We would like to hear your opinion",
                                imageId = R.drawable.ic_book,
                                btnText = "Leave a review"
                            ) { }
                            ActionCard(
                                title = "About us",
                                description = "Few words about us",
                                imageId = R.drawable.about_svgrepo_com,
                                btnText = "About us"
                            ) {

                            }

                        }
                    }
                }
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=1280dp,height=800dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
fun GreetingPreview() {
    ApartmentInfoAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF2F4F5)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, bottom = 15.dp, top = 15.dp, end = 40.dp)
            ) {
            }
        }
    }
}



