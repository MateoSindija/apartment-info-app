package com.example.apartmentinfoapp.presentation.activities

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apartmentinfoapp.R
import com.example.apartmentinfoapp.presentation.composables.ActivityLayout
import com.example.apartmentinfoapp.presentation.composables.RatingCard
import com.example.apartmentinfoapp.presentation.states.ReviewExistanceState
import com.example.apartmentinfoapp.presentation.ui.theme.Typography
import com.example.apartmentinfoapp.presentation.viewmodels.ReviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewActivity : ComponentActivity() {
    private val viewModelReviews: ReviewViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        viewModelReviews.checkIfReviewExists()
        actionBar?.hide()
        setContent {
            var reviewField by remember { mutableStateOf("") }
            var expRating by remember { mutableStateOf(0) }
            var comfortRating by remember { mutableStateOf(0) }
            var valueRating by remember { mutableStateOf(0) }
            val reviewState by viewModelReviews.state.collectAsState()

            ActivityLayout {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = 30.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(230.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.review_background),
                            contentDescription = "Review background",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .innerShadow(
                                    shape = RectangleShape,
                                    color = Color.Black.copy(0.5f),
                                    offsetY = (-300).dp,
                                    offsetX = (0).dp,
                                    blur = 10.dp,
                                    spread = 10.dp
                                )
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Transparent)
                                .padding(start = 85.dp, bottom = 30.dp),
                            contentAlignment = Alignment.BottomStart
                        ) {
                            Column {
                                Text(
                                    text = "Leave a review",
                                    style = Typography.bodyLarge,
                                    color = colorResource(id = R.color.white),
                                    fontWeight = FontWeight.W500,
                                    fontSize = 54.sp
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 85.dp, end = 85.dp, top = 29.dp, bottom = 10.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (reviewState.isReviewAlreadySubmitted != true) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                RatingCard(
                                    header = "How was your overall experience?",
                                    subHeader = "Rate your stay to help us improve your experience",
                                    onRatingChange = { expRating = it },
                                    rating = expRating,
                                    imageResource = painterResource(
                                        id = R.drawable.ic_smile
                                    )
                                )
                                RatingCard(
                                    header = "How comfortable was your stay?",
                                    subHeader = "Rate the comfort of your stay to help us ensure a restful experiences",
                                    onRatingChange = { comfortRating = it },
                                    rating = comfortRating,
                                    imageResource = painterResource(
                                        id = R.drawable.ic_desk_chair
                                    )
                                )
                                RatingCard(
                                    header = "How was the value for your money?",
                                    subHeader = "Rate the value to help us provide better experiences",
                                    onRatingChange = { valueRating = it },
                                    rating = valueRating,
                                    imageResource = painterResource(
                                        id = R.drawable.ic_euro
                                    )
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(130.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                TextField(
                                    modifier = Modifier
                                        .fillMaxWidth(0.7f)
                                        .fillMaxHeight()
                                        .padding(10.dp)
                                        .border(
                                            width = 1.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(8.dp)
                                        ),
                                    placeholder = { Text(text = "Write your review here (optional)") },
                                    value = reviewField,
                                    shape = RoundedCornerShape(5.dp),
                                    colors = TextFieldDefaults.colors(
                                        focusedIndicatorColor = Color.Transparent, // Hide the underline indicator
                                        unfocusedIndicatorColor = Color.Transparent,
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = colorResource(id = R.color.gains_boro)
                                    ),
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Done
                                    ),
                                    onValueChange = { reviewField = it },
                                    maxLines = 10,
                                )
                                Spacer(modifier = Modifier.width(30.dp))
                                Button(
                                    onClick = {
                                        submitReview(
                                            reviewField = reviewField,
                                            expRating = expRating,
                                            comfortRating = comfortRating,
                                            valueRating = valueRating,
                                            reviewState = reviewState
                                        )
                                        reviewField = ""
                                        expRating = 0
                                        comfortRating = 0
                                        valueRating = 0

                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(
                                            id = R.color.tufts_blue
                                        )
                                    ),
                                    shape = RoundedCornerShape(5.dp),
                                    modifier = Modifier
                                        .width(120.dp)
                                        .height(50.dp)
                                ) {
                                    Text(
                                        text = "Submit",
                                        fontSize = 18.sp,
                                        color = colorResource(id = R.color.white)
                                    )
                                }
                            }
                        } else {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(
                                    text = "Thank you for your review",
                                    fontSize = 20.sp,
                                    style = Typography.bodyLarge,
                                    color = colorResource(id = R.color.black),
                                    fontWeight = FontWeight.W500,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun submitReview(
        reviewField: String,
        expRating: Int,
        comfortRating: Int,
        valueRating: Int,
        reviewState: ReviewExistanceState,
    ) {
        if (expRating == 0 || comfortRating == 0 || valueRating == 0) return
        viewModelReviews.submitReview(reviewField, expRating, comfortRating, valueRating)
        reviewState.isReviewAlreadySubmitted = true
    }
}

