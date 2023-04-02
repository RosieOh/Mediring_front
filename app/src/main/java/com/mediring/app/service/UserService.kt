package com.mediring.app.service

import com.mediring.app.model.AddressEntity
import com.mediring.app.model.UserEntity
import com.mediring.app.model.request.RequestAddress
import com.mediring.app.model.request.RequestLogin
import com.mediring.app.model.request.RequestUser
import com.mediring.app.model.response.ResponseLogin
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST("/api/user/store")
    fun store(@Body requestUser: RequestUser): Call<UserEntity>

//    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("/oauth/token")
    fun login(@Field("username") username: String, @Field("password") password: String, @Field("grant_type") grantType: String,
              @Field("scope") scope: String, @Field("client_secret") clientSecret: String, @Field("client_id") clientId: String): Call<ResponseLogin>

    @POST("/session/login")
    fun sessionLogin(@Body request: RequestLogin): Call<UserEntity>

    @POST("/api/user/update")
    fun editUser(@Body requestUser: RequestUser): Call<UserEntity>

    @POST("/api/address/store")
    fun editAddress(@Body requestAddress: RequestAddress): Call<AddressEntity>

    @GET("/auth/revoke")
    fun logout()
}