package com.example.apartmentinfoapp.presentation.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apartmentinfoapp.presentation.models.CommonCardData
import com.example.apartmentinfoapp.presentation.models.CommonDataState


@Composable
fun CardsList(state: CommonDataState) {

    when (state) {
        is CommonDataState.BeachCardList -> {
            state.let { data ->
                if (data.beachState?.beachInfo?.isNotEmpty() == true) {
                    LazyRow(content = {
                        items(count = data.beachState.beachInfo.size, key = {
                            data.beachState.beachInfo[it]?.id
                                ?: ""
                        }, itemContent = { index ->
                            AttractionCard(
                                CommonCardData.BeachCard(
                                   data.beachState.beachInfo[index],
                                    data.beachState.mineLat,
                                    data.beachState.mineLng
                                )
                            )
                            Spacer(modifier = Modifier.width(30.dp))

                        })
                    })
                }
            }
        }

        is CommonDataState.RestaurantCardList -> {
            state.let { data ->
                if (data.restaurantState?.restaurantsInfoList?.isNotEmpty() == true) {
                    LazyRow(content = {
                        items(count = data.restaurantState.restaurantsInfoList.size, key = {
                            data.restaurantState.restaurantsInfoList[it]?.id
                                ?: ""
                        }, itemContent = { index ->
                            AttractionCard(
                                CommonCardData.RestaurantCard(
                                    data.restaurantState.restaurantsInfoList[index],
                                    data.restaurantState.mineLat,
                                    data.restaurantState.mineLng
                                )
                            )
                            Spacer(modifier = Modifier.width(30.dp))

                        })
                    })
                }
            }
        }
    }
}