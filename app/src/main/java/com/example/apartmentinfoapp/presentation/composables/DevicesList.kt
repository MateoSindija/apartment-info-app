package com.example.apartmentinfoapp.presentation.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apartmentinfoapp.presentation.states.DevicesListState

@Composable
fun DevicesList(devicesListState: DevicesListState, modifier: Modifier) {

    devicesListState.devicesInfoList.let { devicesDataList ->
        if (devicesDataList?.isNotEmpty() == true) {
            LazyColumn(
                modifier = modifier,
                horizontalAlignment = Alignment.Start,
                content = {
                    items(devicesDataList) { deviceData ->
                        DeviceButton(deviceData)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                })
        }
    }

}
