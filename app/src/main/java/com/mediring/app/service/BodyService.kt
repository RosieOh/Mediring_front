package com.mediring.app.service

import com.mediring.app.model.BodyEntity
import com.mediring.app.model.ProductEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BodyService {
    @GET("/api/body")
    fun getBodyList(): Call<List<BodyEntity>>

    @GET("/api/body/part/{id}")
    fun getPartList(@Path("id") id: Int): Call<List<ProductEntity>>
}