package com.example.apartmentinfoapp.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.unit.dp
import com.example.apartmentinfoapp.R
import com.example.apartmentinfoapp.domain.devices.DeviceData

fun handleClick() {

}

@Composable
fun DeviceButton(deviceData: DeviceData) {

    OutlinedButton(
        onClick = { handleClick() }, shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, Color.White),
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .clip(CircleShape)
            .background(Color.White)


    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = deviceData.title ?: "",
                color = colorResource(id = R.color.tufts_blue),
                fontWeight = FontWeight.W500
            )
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "arrow right"
            )
        }
    }
}
