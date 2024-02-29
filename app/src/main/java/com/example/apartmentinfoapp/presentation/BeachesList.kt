package com.example.apartmentinfoapp.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

private fun Double.roundTo2DecimalPlaces(): Double {
    return (this * 100.0).roundToInt() / 100.0
}

@Composable
fun BeachesList(state: BeachState, modifier: Modifier) {



    state.let { beachesData ->
        if (beachesData.beachInfo?.isNotEmpty() == true) {
            LazyRow(modifier = modifier, content = {
                items(beachesData.beachInfo) { beachData ->
                    BeachCard(beachData, state.mineLat, state.mineLng, state.weatherResult)
                    Spacer(modifier = Modifier.width(30.dp))

                }
            })
        }
    }

}