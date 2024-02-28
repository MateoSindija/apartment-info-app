package com.example.apartmentinfoapp.data.mappers


import android.os.Build
import androidx.annotation.RequiresApi
import com.example.apartmentinfoapp.data.remote.DailyLengthDataDto
import com.example.apartmentinfoapp.data.remote.WeatherDataDto
import com.example.apartmentinfoapp.data.remote.WeatherDto
import com.example.apartmentinfoapp.domain.weather.DailyLengthData
import com.example.apartmentinfoapp.domain.weather.WeatherData
import com.example.apartmentinfoapp.domain.weather.WeatherInfo
import com.example.apartmentinfoapp.domain.weather.WeatherType
import java.time.LocalDate

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

 data class IndexedDailyLengthData(
    val index: Int,
    val data: DailyLengthData
)

@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        val precipitation = precipitation[index]
        val visibility = visibility[index]
        val windDirection = windDirection[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode),
                precipitation = precipitation,
                visibility = visibility,
                windDirection = windDirection,
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun DailyLengthDataDto.toDailyDataMap(): List<IndexedDailyLengthData> {
    return time.mapIndexed { index, time ->
        val sunrise = sunrise[index]
        val sunset = sunset[index]
        IndexedDailyLengthData(
            index = index,
            data = DailyLengthData(
                time = time,
                sunrise = LocalDateTime.parse(sunrise, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                sunset = LocalDateTime.parse(sunset, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val dailyLengthDataMap = dailyLengthData.toDailyDataMap()
    val now = LocalDateTime.now()

    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }




    return WeatherInfo(
        currentDayLengthData = dailyLengthDataMap[0] ,
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}
