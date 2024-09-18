package com.example.apartmentinfoapp.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val accessToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("x-access-token", accessToken)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
