package com.example.apartmentinfoapp.presentation.composables

import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.apartmentinfoapp.R
import com.example.apartmentinfoapp.domain.sights.SightDataDto
import com.example.apartmentinfoapp.presentation.activities.distanceBetweenPoints
import com.example.apartmentinfoapp.presentation.models.CommonCardData


@Composable
fun ImageCard(
    modifier: Modifier,
    data: SightDataDto?,
    mineLat: Double?,
    mineLng: Double?,
    textSizeTitle: TextUnit = 16.sp,
    textSizeSubHeader: TextUnit = 14.sp,
    padding: Dp = 20.dp
) {
    val context = LocalContext.current
    Card(
        modifier = modifier,
        onClick = {
            launchNextActivity(
                CommonCardData.SightCard(data, mineLat, mineLng),
                context
            )
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(
                    data?.imagesUrl?.get(0)
                ).crossfade(true).build(),
                placeholder = painterResource(id = R.drawable.ic_image_loading),
                contentDescription = "Sight Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .innerShadow(
                        shape = RectangleShape,
                        color = Color.Black.copy(0.5f),
                        offsetY = (-500).dp,
                        offsetX = (0).dp,
                        blur = 10.dp,
                        spread = 10.dp
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.BottomStart
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.25f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = data?.title ?: "",
                        color = colorResource(id = R.color.white),
                        fontWeight = FontWeight(500),
                        fontSize = textSizeTitle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.location_pin),
                            contentDescription = "pin",
                            modifier = Modifier.height(18.dp)
                        )
                        Spacer(Modifier.width(5.dp))
                        Text(
                            text = distanceBetweenPoints(
                                data?.location?.coordinates?.get(1),
                                data?.location?.coordinates?.get(0),
                                mineLat,
                                mineLng
                            ),
                            modifier = Modifier.weight(1f),
                            color = colorResource(id = R.color.white),
                            fontWeight = FontWeight(500),
                            fontSize = textSizeSubHeader
                        )
                    }
                }
            }
        }
    }
}

fun Modifier.innerShadow(
    shape: androidx.compose.ui.graphics.Shape,
    color: Color,
    blur: Dp,
    offsetY: Dp,
    offsetX: Dp,
    spread: Dp
) = drawWithContent {
    drawContent()
    val rect = Rect(Offset.Zero, size)
    val paint = Paint().apply {
        this.color = color
        this.isAntiAlias = true
    }
    val shadowOutline = shape.createOutline(size, layoutDirection, this@drawWithContent)

    drawIntoCanvas { canvas ->

        // Save the current layer.
        canvas.saveLayer(rect, paint = paint)
        // Draw the first layer of the shadow.
        canvas.drawOutline(shadowOutline, paint)

        // Convert the paint to a FrameworkPaint.
        val frameworkPaint = paint.asFrameworkPaint()
        // Set xfermode to DST_OUT to create the inner shadow effect.
        frameworkPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)

        // Apply blur if specified.
        if (blur.toPx() > 0) {
            frameworkPaint.maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }

        // Change paint color to black for the inner shadow.
        paint.color = Color.Black

        // Calculate offsets considering spread.
        val spreadOffsetX =
            offsetX.toPx() + if (offsetX.toPx() < 0) -spread.toPx() else spread.toPx()
        val spreadOffsetY =
            offsetY.toPx() + if (offsetY.toPx() < 0) -spread.toPx() else spread.toPx()

        // Move the canvas to specific offsets.
        canvas.translate(spreadOffsetX, spreadOffsetY)

        // Draw the second layer of the shadow.
        canvas.drawOutline(shadowOutline, paint)

        // Restore the canvas to its original state.
        canvas.restore()
    }
}
