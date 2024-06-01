package com.example.apartmentinfoapp.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.sp
import com.example.apartmentinfoapp.R
import com.example.apartmentinfoapp.presentation.ui.theme.Typography

@Composable
fun ActionCard(
    title: String,
    description: String,
    imageId: Int,
    btnText: String,
    handleClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(365.dp)
            .height(215.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = Color(0f, 0f, 0f, 0.25f),
                clip = true
            )
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(23.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2.5f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = title,
                        style = Typography.bodyLarge,
                        color = colorResource(id = R.color.tufts_blue),
                        fontSize = 30.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = description,
                        style = Typography.bodyMedium,
                        color = colorResource(id = R.color.space_cadet)
                    )
                }
                OutlinedButton(
                    onClick = handleClick, shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, colorResource(id = R.color.tufts_blue)),
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .clip(CircleShape)
                        .background(colorResource(id = R.color.tufts_blue))


                ) {
                    Text(
                        text = btnText,
                        color = Color.White,
                        fontWeight = FontWeight.W500
                    )
                }
            }
            Image(
                painter = painterResource(id = imageId),
                contentDescription = null,
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxHeight()
            )
        }
    }
}