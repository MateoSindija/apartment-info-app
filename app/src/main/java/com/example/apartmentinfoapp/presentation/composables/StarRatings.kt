package com.example.apartmentinfoapp.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.apartmentinfoapp.R

@Composable
fun StarRatings(modifier: Modifier, selectedRating: Int, onRatingSelected: (Int) -> Unit) {


    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            val star =
                if (i <= selectedRating) R.drawable.ic_star_full else R.drawable.ic_star_empty
            Image(
                painter = painterResource(id = star),
                contentDescription = null,
                modifier = modifier.clickable {
                    onRatingSelected(i)
                }
            )
        }
    }
}