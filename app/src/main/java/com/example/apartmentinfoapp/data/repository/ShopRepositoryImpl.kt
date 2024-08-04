package com.example.apartmentinfoapp.data.repository

import com.example.apartmentinfoapp.data.mappers.formatImages
import com.example.apartmentinfoapp.data.remote.ApartmentApi
import com.example.apartmentinfoapp.domain.repository.ShopRepository
import com.example.apartmentinfoapp.domain.shops.ShopDataDto
import com.example.apartmentinfoapp.domain.util.Resource
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(private val api: ApartmentApi) : ShopRepository {


    override suspend fun getShopsList(apartmentId: String): Resource<List<ShopDataDto>> {
        return try {

            Resource.Success(data = api.getShops(apartmentId).formatImages())

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}