package com.mediring.app.service

import com.mediring.app.model.LikeProductEntity
import com.mediring.app.model.request.RequestLikeProduct
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LikeProductService {
    @GET("/api/like/list/{id}")
    fun list(@Path("id") userId: Int): Call<List<LikeProductEntity>>

    @POST("/api/like/store")
    fun like(@Body request: RequestLikeProduct): Call<LikeProductEntity>

    @POST("/api/like/delete")
    fun delete(@Body request: RequestLikeProduct): Call<Boolean>
}