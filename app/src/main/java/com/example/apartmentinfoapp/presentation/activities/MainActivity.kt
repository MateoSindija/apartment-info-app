package com.example.apartmentinfoapp.presentation.activities

import android.content.Context
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
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.apartmentinfoapp.BuildConfig
import com.example.apartmentinfoapp.R
import com.example.apartmentinfoapp.presentation.composables.ActionCard
import com.example.apartmentinfoapp.presentation.composables.ActivityLayout
import com.example.apartmentinfoapp.presentation.composables.CardsList
import com.example.apartmentinfoapp.presentation.composables.DevicesList
import com.example.apartmentinfoapp.presentation.composables.InfoCardBig
import com.example.apartmentinfoapp.presentation.composables.InfoCardSmall
import com.example.apartmentinfoapp.presentation.composables.PullToRefreshComponent
import com.example.apartmentinfoapp.presentation.composables.SightGrid
import com.example.apartmentinfoapp.presentation.composables.launchNextActivity
import com.example.apartmentinfoapp.presentation.models.CommonCardData
import com.example.apartmentinfoapp.presentation.models.CommonDataState
import com.example.apartmentinfoapp.presentation.ui.theme.ApartmentInfoAppTheme
import com.example.apartmentinfoapp.presentation.ui.theme.Typography
import com.example.apartmentinfoapp.presentation.viewmodels.AboutUsViewModel
import com.example.apartmentinfoapp.presentation.viewmodels.BeachViewModel
import com.example.apartmentinfoapp.presentation.viewmodels.DevicesViewModel
import com.example.apartmentinfoapp.presentation.viewmodels.ReservationViewModel
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

public fun formatImageUrl(imgUrl: String): String {
    val serverLocation = BuildConfig.SERVER_LOCATION
    return "$serverLocation/${imgUrl.replace("public/", "")}"
}

public fun formatImageUrls(imgUrls: List<String>): List<String> {
    val serverLocation = BuildConfig.SERVER_LOCATION
    return imgUrls.map { "$serverLocation/${it.replace("public/", "")}" }
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
    private val viewModelAboutUs: AboutUsViewModel by viewModels()
    private val viewModelReservation: ReservationViewModel by viewModels()
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
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.ACCESS_NETWORK_STATE
            )
        )


        fun getDayGreeting(clientName: String): String {

            return when (LocalTime.now().hour) {
                in 0..5 -> "Good night ${clientName}"
                in 6..11 -> "Good morning ${clientName}"
                in 12..17 -> "Good afternoon ${clientName}"
                in 18..21 -> "Good evening ${clientName}"
                else -> "Good night ${clientName}"
            }
        }


        setContent {
            val pullToRefreshState = rememberPullToRefreshState()
            val beachState by viewModelBeaches.state.collectAsState()
            val devicesState by viewModelDevices.state.collectAsState()
            val restaurantsState by viewModelRestaurants.state.collectAsState()
            val sightsState by viewModelSights.state.collectAsState()
            val weatherState by viewModelWeather.state.collectAsState()
            val shopsState by viewModelShops.state.collectAsState()
            val reservationState by viewModelReservation.state.collectAsState()
            var isSignOutDialogOpen by remember { mutableStateOf<Boolean>(false) }


            ActivityLayout(modifier = Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, bottom = 15.dp, top = 15.dp, end = 40.dp)
                        .verticalScroll(rememberScrollState())
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
                        pullToRefreshState = pullToRefreshState,
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
                                    text = getDayGreeting(
                                        reservationState.reservationInfo?.clientName ?: ""
                                    ),
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
                            launchNextActivity(
                                CommonCardData.AboutUsCard(viewModelAboutUs.state.value.aboutUsInfo),
                                this@MainActivity
                            )
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

                    Spacer(modifier = Modifier.height(25.dp))
                    if (isSignOutDialogOpen) {
                        FullScreenSignOutDialog(
                            onClose = { isSignOutDialogOpen = false },
                            context = this@MainActivity
                        )
                    }
                    OutlinedButton(
                        onClick = { isSignOutDialogOpen = true },
                        modifier = Modifier
                            .height(50.dp)
                            .width(150.dp)
                            .background(Color.Transparent)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Sign out", color = Color.Black)
                            Spacer(modifier = Modifier.width(20.dp))
                            Image(
                                painter = painterResource(id = R.drawable.ic_log_out),
                                contentDescription = "logout",
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(20.dp)
                            )
                        }

                    }
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
        viewModelAboutUs.loadAboutUsInfo()
        viewModelReservation.loadReservationInfo(this)
    }

}

private fun signOut(context: Context) {
    val sharedPref = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString("x-access-token", "").apply()
        putString("apartmentId", "").apply()
        putString("ownerId", "").apply()
    }
    val attractionIntent = Intent(context, LoginActivity::class.java)
    context.startActivity(attractionIntent)
}

@Composable
fun FullScreenSignOutDialog(onClose: () -> Unit, context: Context) {

    Dialog(onClose) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(25.dp)
        ) {
            Text(text = "Are you sure you want to sign out?", color = Color.Black)
            Spacer(modifier = Modifier.height(25.dp))
            Row {
                OutlinedButton(
                    onClick = { signOut(context) },
                    border = BorderStroke(1.dp, colorResource(id = R.color.tufts_blue)),
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .clip(CircleShape)
                        .background(colorResource(id = R.color.tufts_blue))
                ) {
                    Text(text = "Sign out", color = Color.White)
                }
                Spacer(modifier = Modifier.width(10.dp))
                OutlinedButton(
                    onClick = onClose,
                    border = BorderStroke(1.dp, Color.Red),
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                ) {
                    Text(text = "Cancel", color = Color.White)
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
    val context = LocalContext.current
    ApartmentInfoAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF2F4F5)
        ) {
            FullScreenSignOutDialog(
                onClose = { },
                context = context
            )
        }
    }
}



