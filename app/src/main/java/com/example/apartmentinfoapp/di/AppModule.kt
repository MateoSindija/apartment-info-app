package com.example.apartmentinfoapp.di

import ChatSocketServiceImpl
import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.apartmentinfoapp.BuildConfig
import com.example.apartmentinfoapp.data.interceptor.AccessTokenProvider
import com.example.apartmentinfoapp.data.interceptor.AuthInterceptor
import com.example.apartmentinfoapp.data.remote.ApartmentApi
import com.example.apartmentinfoapp.data.remote.ChatSocketService
import com.example.apartmentinfoapp.data.remote.LoginApi
import com.example.apartmentinfoapp.data.remote.WeatherApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.O)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val serverLocation = BuildConfig.SERVER_LOCATION

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideAuthApi(): LoginApi {
        return Retrofit.Builder().baseUrl(serverLocation)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideApartmentApi(accessTokenProvider: AccessTokenProvider): ApartmentApi {
        val moshi = Moshi.Builder()
            .add(LocalDateTimeJsonAdapter())
            .build()


        val accessToken = accessTokenProvider.getAccessToken()

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(accessToken))
            .build()




        return Retrofit.Builder()
            .baseUrl(serverLocation)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApartmentApi::class.java)
    }


    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }

    @Provides
    @Singleton
    fun provideAccessTokenProvider(@ApplicationContext context: Context): AccessTokenProvider {
        return AccessTokenProvider(context)
    }

    @Provides
    @Singleton
    fun provideChatSocketService(): ChatSocketService {
        return ChatSocketServiceImpl()
    }

}

@RequiresApi(Build.VERSION_CODES.O)
class LocalDateTimeJsonAdapter {

    private val FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @FromJson
    fun fromJson(json: String): LocalDateTime? {
        return try {
            val offsetDateTime = OffsetDateTime.parse(json, FORMATTER)
            offsetDateTime.toLocalDateTime()
        } catch (e: DateTimeParseException) {
            throw RuntimeException("Failed to parse date: $json", e)
        }
    }

    @ToJson
    fun toJson(value: LocalDateTime): String? {
        return value.atOffset(ZoneOffset.UTC).format(FORMATTER)
    }
}