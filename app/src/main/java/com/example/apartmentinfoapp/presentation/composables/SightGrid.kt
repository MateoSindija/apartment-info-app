package com.example.apartmentinfoapp.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apartmentinfoapp.presentation.states.SightsState

val CHILDREN_PADDING = 20.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SightGrid(state: SightsState) {
    Column {

        Row(
            modifier = Modifier
                .padding(bottom = CHILDREN_PADDING)
                .height(420.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(end = CHILDREN_PADDING)
            ) {
                ImageCard(
                    modifier = Modifier
                        .fillMaxSize(),
                    data = state.sightsInfoList?.get(0),
                    mineLat = state.mineLat,
                    mineLng = state.mineLng,
                    textSizeTitle = 28.sp,
                    textSizeSubHeader = 26.sp,
                    padding = 20.dp,

                )
            }
            Column(
                modifier = Modifier
                    .weight(3f)
            ) {
                FlowRow(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (i in 1..7) {
                        if (i < (state.sightsInfoList?.size ?: 1)) {
                            ImageCard(
                                modifier = Modifier
                                    .height(200.dp)
                                    .width(220.dp),
                                data = state.sightsInfoList?.get(i),
                                mineLat = state.mineLat,
                                mineLng = state.mineLng,
                            )
                        }
                    }
                }
            }
        }
        if ((state.sightsInfoList?.size ?: 0) > 7) {
            FlowRow(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (i in 7..(state.sightsInfoList?.size ?: 7)) {
                    ImageCard(
                        modifier = Modifier
                            .height(200.dp)
                            .width(220.dp)
                            .padding(20.dp),
                        data = state.sightsInfoList?.get(i),
                        mineLat = state.mineLat,
                        mineLng = state.mineLng
                    )
                }
            }
        }
    }
}
