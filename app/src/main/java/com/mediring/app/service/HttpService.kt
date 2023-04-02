package com.mediring.app.service

import android.content.Context
import android.util.Log
import androidx.core.os.BuildCompat
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.mediring.app.App
import com.mediring.app.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HostnameVerifier

object HttpService {
    private const val TAG = "HttpService"
    private val gson = GsonBuilder().setLenient().create()

    fun getBodyService(context: Context): BodyService {
        val fit = Retrofit.Builder()
            .baseUrl(App.SERVER_URL)
            .client(getClient(context))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return fit.create(BodyService::class.java)
    }

    fun getProductService(context: Context): ProductService {
        val fit = Retrofit.Builder()
            .baseUrl(App.SERVER_URL)
            .client(getClient(context))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return fit.create(ProductService::class.java)
    }

    fun getUserService(context: Context): UserService {
        val fit = Retrofit.Builder()
                .baseUrl(App.SERVER_URL)
                .client(getClient(context))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        return fit.create(UserService::class.java)
    }

    fun getLikeProductService(context: Context): LikeProductService {
        val fit = Retrofit.Builder()
                .baseUrl(App.SERVER_URL)
                .client(getClient(context))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        return fit.create(LikeProductService::class.java)
    }

    private fun getClient(context: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }
        builder.addInterceptor(logging)

        builder.hostnameVerifier(HostnameVerifier { hostname, session ->
            hostname?.let { hostname ->
                if (hostname.contentEquals(App.SERVER_URL)) {
                    Log.d(TAG, "certificate host : $hostname")
                    true
                } else {
                    Log.e(TAG, "certificate fail")
                    false;
                }
            } ?: run {
                false
            }
        })
        App.accessToken?.let { token ->
            val type = "bearer"
            builder.addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "$type $token")
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .build()
                chain.proceed(newRequest)
            }
        }
        return builder.build()
    }
}