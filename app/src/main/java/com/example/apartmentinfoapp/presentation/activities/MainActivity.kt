package com.example.apartmentinfoapp.presentation.activities

import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apartmentinfoapp.R
import com.example.apartmentinfoapp.presentation.composables.ActionCard
import com.example.apartmentinfoapp.presentation.composables.ActivityLayout
import com.example.apartmentinfoapp.presentation.composables.CardsList
import com.example.apartmentinfoapp.presentation.composables.DevicesList
import com.example.apartmentinfoapp.presentation.composables.InfoCardBig
import com.example.apartmentinfoapp.presentation.composables.InfoCardSmall
import com.example.apartmentinfoapp.presentation.composables.PullToRefreshComponent
import com.example.apartmentinfoapp.presentation.composables.SightGrid
import com.example.apartmentinfoapp.presentation.models.CommonDataState
import com.example.apartmentinfoapp.presentation.ui.theme.ApartmentInfoAppTheme
import com.example.apartmentinfoapp.presentation.ui.theme.Typography
import com.example.apartmentinfoapp.presentation.viewmodels.BeachViewModel
import com.example.apartmentinfoapp.presentation.viewmodels.DevicesViewModel
import com.example.apartmentinfoapp.presentation.viewmodels.RestaurantViewModel
import com.example.apartmentinfoapp.presentation.viewmodels.ShopViewModel
import com.example.apartmentinfoapp.presentation.viewmodels.SightViewModel
import com.example.apartmentinfoapp.presentation.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalTime

fun distanceBetweenPoints(
    startLat: Double?,
    startLng: Double?,
    endLat: Double?,
    endLng: Double?
): String {
    val distanceInKm = distanceBetweenPointsInKm(startLat, startLng, endLat, endLng)
    val distanceInM = distanceBetweenPointsInM(startLat, startLng, endLat, endLng)

    if (distanceInKm != 0) {
        return "$distanceInKm km"
    }
    return "$distanceInM m"
}

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
    private val viewModelSights: SightViewModel by viewModels()
    private val viewModelShops: ShopViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            loadData()

        }
        permissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.INTERNET
            )
        )


        fun getDayGreeting(): String {

            return when (LocalTime.now().hour) {
                in 0..5 -> "Good night"
                in 6..11 -> "Good morning"
                in 12..17 -> "Good afternoon"
                in 18..21 -> "Good evening"
                else -> "Good night"
            }
        }

        fun isContentLoading(
            beachesLoading: Boolean,
            devicesLoading: Boolean,
            restaurantsLoading: Boolean,
            sightsLoading: Boolean,
            weatherLoading: Boolean,
            shopsLoading: Boolean
        ): Boolean {
            return beachesLoading || devicesLoading || restaurantsLoading || sightsLoading || weatherLoading || shopsLoading
        }



        setContent {
            val pullToRefreshState = rememberPullToRefreshState()
            val beachState by viewModelBeaches.state.collectAsState()
            val devicesState by viewModelDevices.state.collectAsState()
            val restaurantsState by viewModelRestaurants.state.collectAsState()
            val sightsState by viewModelSights.state.collectAsState()
            val weatherState by viewModelWeather.state.collectAsState()
            val shopsState by viewModelShops.state.collectAsState()


            ActivityLayout() {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, bottom = 15.dp, top = 15.dp, end = 40.dp)
                        .verticalScroll(rememberScrollState())
                        .nestedScroll(pullToRefreshState.nestedScrollConnection),
                ) {
                    PullToRefreshComponent(
                        isLoadingStates = listOf(
                            weatherState.isLoading,
                            beachState.isLoading,
                            devicesState.isLoading,
                            restaurantsState.isLoading,
                            sightsState.isLoading,
                            shopsState.isLoading
                        ),
                        modifier = Modifier.align(CenterHorizontally)
                    ) { loadData() }

                    Row(Modifier.height(210.dp)) {
                        Column(modifier = Modifier.weight(2f)) {
                            Row(
                                modifier = Modifier
                                    .height(80.dp)
                                    .width(700.dp)
                            ) {
                                Text(
                                    text = getDayGreeting(),
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
                                    weatherState
                                )
                                InfoCardSmall(
                                    state = weatherState,
                                    drawable = R.drawable.wind_icon_com,
                                    type = "Wind",
                                )
                                InfoCardSmall(
                                    type = "Precipitation",
                                    drawable = R.drawable.rain_icon,
                                    state = weatherState
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .weight(1.3f)
                                .padding(start = 35.dp)
                                .fillMaxSize(),
                            horizontalAlignment = CenterHorizontally,
                        ) {
                            InfoCardBig(weatherState)
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
                                    text = "Beaches (${beachState.beachInfo?.size})",
                                    style = Typography.bodyLarge,
                                    color = colorResource(id = R.color.space_cadet),
                                    fontWeight = FontWeight(500)
                                )
                                Spacer(modifier = Modifier.height(15.dp))
                                CardsList(CommonDataState.BeachCardList(beachState))
                            }

                            Column {
                                Text(
                                    text = "Restaurants (${restaurantsState.restaurantsInfoList?.size})",
                                    style = Typography.bodyLarge,
                                    color = colorResource(id = R.color.space_cadet),
                                    fontWeight = FontWeight(500)
                                )
                                Spacer(modifier = Modifier.height(15.dp))
                                CardsList(
                                    CommonDataState.RestaurantCardList(
                                        restaurantsState
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
                                text = "Devices (${devicesState.devicesInfoList?.size})",
                                style = Typography.bodyLarge,
                                color = colorResource(id = R.color.space_cadet),
                                fontWeight = FontWeight(500)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            DevicesList(
                                devicesState,
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
                        ) {
                            val messageIntent =
                                Intent(this@MainActivity, MessageActivity::class.java)
                            startActivity(messageIntent)
                        }
                        ActionCard(
                            title = "Guestbook",
                            description = "We would like to hear your opinion",
                            imageId = R.drawable.ic_book,
                            btnText = "Leave a review"
                        ) {
                            val reviewIntent =
                                Intent(this@MainActivity, ReviewActivity::class.java)
                            startActivity(reviewIntent)
                        }
                        ActionCard(
                            title = "About us",
                            description = "Few words about us",
                            imageId = R.drawable.about_svgrepo_com,
                            btnText = "About us"
                        ) {
                            val attractionIntent =
                                Intent(this@MainActivity, AttractionActivity::class.java)
                            startActivity(attractionIntent)
                        }

                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = "Sights (${sightsState.sightsInfoList?.size})",
                        style = Typography.bodyLarge,
                        color = colorResource(id = R.color.space_cadet),
                        fontWeight = FontWeight(500)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    SightGrid(sightsState)
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = "Shops (${shopsState.shopsInfoList?.size})",
                        style = Typography.bodyLarge,
                        color = colorResource(id = R.color.space_cadet),
                        fontWeight = FontWeight(500)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    CardsList(state = CommonDataState.ShopCardList(shopsState))
                }

            }
        }
    }


    private fun loadData() {
        viewModelWeather.loadWeatherInfo()
        viewModelDevices.loadDevicesList()
        viewModelRestaurants.loadRestaurantsInfo()
        viewModelSights.loadSightsInfo()
        viewModelBeaches.loadBeachesInfo()
        viewModelShops.loadShopsInfo()
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



