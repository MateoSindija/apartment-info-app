package com.example.apartmentinfoapp.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apartmentinfoapp.R
import com.example.apartmentinfoapp.presentation.ui.theme.ApartmentInfoAppTheme
import com.example.apartmentinfoapp.presentation.ui.theme.Typography
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModelWeather: WeatherViewModel by viewModels()
    private val viewModelBeaches: BeachViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModelWeather.loadWeatherInfo()
            viewModelBeaches.loadBeachesInfo()
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp, bottom = 15.dp, top = 15.dp, end = 40.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()

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
                            Text(
                                text = "Beaches",
                                style = Typography.bodyLarge,
                                color = colorResource(id = R.color.space_cadet),
                                fontWeight = FontWeight(500)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(210.dp)
                            ) {
                                BeachesList(viewModelBeaches.state, Modifier.weight(3f))
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



