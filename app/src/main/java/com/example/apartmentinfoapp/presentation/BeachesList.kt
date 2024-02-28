package com.example.apartmentinfoapp.presentation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RowScope.BeachesList(state: BeachState, modifier: Modifier) {

    state.beachInfo.let { beachesData ->
        if (beachesData?.isNotEmpty() == true) {
            LazyRow(modifier = modifier, content = {
                items(beachesData) { beachData ->
                    BeachCard(beachData)

                }
            })
        }
    }

}