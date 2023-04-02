package com.mediring.app.service

import com.mediring.app.model.ProductEntity
import com.mediring.app.model.ReviewEntity
import com.mediring.app.model.ShoppingBasketEntity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductService {
    @GET("/api/product")
    fun getProductList(): Call<List<ProductEntity>>

    @GET ("/api/product/{id}")
    fun getProduct(@Path("id") id: Int): Call<ProductEntity>

    @GET("/api/basket/find/{id}")
    fun getBaskets(@Path("id") userId: Int): Call<List<ShoppingBasketEntity>>

    @GET("/api/basket/delete/{id}")
    fun deleteBasket(@Path("id") id: Int): Call<List<ShoppingBasketEntity>>

    @POST("/api/basket/store")
    fun storeBasket(@Body basketEntity: ShoppingBasketEntity): Call<ShoppingBasketEntity>

    @JvmSuppressWildcards
    @POST("/api/buy-list/store")
    fun storeBuyList(@Body lists: List<ShoppingBasketEntity>): Call<Boolean>

    @GET("/api/review/product/{id}")
    fun getProductReviews(@Path("id") productId: Int): Call<List<ReviewEntity>>
}