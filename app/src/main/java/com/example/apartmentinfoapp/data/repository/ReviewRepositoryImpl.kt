package com.example.apartmentinfoapp.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.example.apartmentinfoapp.data.remote.ApartmentApi
import com.example.apartmentinfoapp.domain.repository.ReviewRepository
import com.example.apartmentinfoapp.domain.util.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(private val api: ApartmentApi) : ReviewRepository {


    private fun createMultipartFromBitmapList(
        bitmaps: List<Bitmap>,
    ): List<MultipartBody.Part> {
        return bitmaps.mapIndexed { index, bitmap ->
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val requestBody = RequestBody.create("image/png".toMediaTypeOrNull(), byteArray)
            MultipartBody.Part.createFormData("images", "image$index.png", requestBody)
        }
    }


    override suspend fun submitReview(
        review: String?,
        expRating: Int,
        comfortRating: Int,
        valueRating: Int,
        apartmentId: String,
        photosState: List<Bitmap>,
    ): Resource<Boolean> {
        return try {

            val reviewRequestBody = review?.toRequestBody("text/plain".toMediaTypeOrNull())
            val comfortRatingRequestBody =
                comfortRating.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val expRatingRequestBody =
                expRating.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val valueRatingRequestBody =
                valueRating.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val apartmentIdRequestBody = apartmentId.toRequestBody("text/plain".toMediaTypeOrNull())
            val imageParts = createMultipartFromBitmapList(bitmaps = photosState)


            api.postReview(
                review = reviewRequestBody,
                comfortRating = comfortRatingRequestBody,
                expRating = expRatingRequestBody,
                valueRating = valueRatingRequestBody,
                apartmentId = apartmentIdRequestBody,
                images = imageParts

            )
            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("tu smo", e.message.toString())
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