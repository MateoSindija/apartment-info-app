package com.example.apartmentinfoapp.data.repository

import com.example.apartmentinfoapp.data.remote.ApartmentApi
import com.example.apartmentinfoapp.domain.repository.ReviewRepository
import com.example.apartmentinfoapp.domain.review.ReviewBody
import com.example.apartmentinfoapp.domain.util.Resource
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(private val api: ApartmentApi) : ReviewRepository {

    override suspend fun submitReview(
        review: String?,
        expRating: Int,
        comfortRating: Int,
        valueRating: Int,
        apartmentId: String,
    ): Resource<Boolean> {
        return try {
            api.postReview(
                ReviewBody(
                    comfortRating = comfortRating,
                    experienceRating = expRating,
                    valueRating = valueRating,
                    apartmentId = apartmentId,
                    review = review,
                )
            )
            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun isReviewAlreadyIsSubmitted(apartmentId: String): Resource<Boolean> {
        return try {
            Resource.Success(data = api.isReviewAlreadySubmitted(apartmentId))
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

}