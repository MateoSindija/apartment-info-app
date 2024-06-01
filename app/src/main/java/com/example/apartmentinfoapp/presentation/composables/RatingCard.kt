package com.example.apartmentinfoapp.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.apartmentinfoapp.R
import com.example.apartmentinfoapp.presentation.ui.theme.ApartmentInfoAppTheme

@Composable
fun RatingCard(
    header: String,
    subHeader: String,
    rating: Int,
    imageResource: Painter,
    onRatingChange: (Int) -> Unit
) {
    val yellowCrayola = colorResource(id = R.color.yellow_crayola)
    Box(
        modifier = Modifier
            .width(340.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(89.dp)
                .zIndex(2f),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()// 100dp image + 2dp border on each side
                    .width(89.dp)
                    .border(
                        BorderStroke(2.dp, Color.White),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = imageResource,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(
                            BorderStroke(2.dp, Color.White),
                            CircleShape
                        ),
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(276.dp)
                .padding(top = 40.dp, start = 2.dp, end = 2.dp)
                .drawBehind {
                    // Draw shadow with rounded corners
                    drawRoundRect(
                        color = Color(0f, 0f, 0f, 0.95f),
                        topLeft = Offset(0f, 0f),
                        size = size,
                        cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx())
                    )
                }
                .background(Color.White)
                .drawBehind {
                    // Draw yellow top border with rounded corners
                    drawRoundRect(
                        color = yellowCrayola,
                        topLeft = Offset(0f, 0f),
                        size = Size(size.width, 15.dp.toPx())
                    )
                }
                .clip(RoundedCornerShape(5.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                text = header,
                color = Color.Black,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                text = subHeader,
                color = colorResource(id = R.color.davy_grey),
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            StarRatings(Modifier.size(45.dp), rating) { newRating ->
                onRatingChange(newRating)
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                }
            }
        }
    }
}

