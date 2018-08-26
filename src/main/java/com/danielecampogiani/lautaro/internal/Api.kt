package com.danielecampogiani.lautaro.internal

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

internal interface Api {

    companion object {
        operator fun invoke(retrofit: Retrofit = instance): Api {
            return retrofit.create(Api::class.java)
        }

        private val instance: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://www.reddit.com/r/soccerstreams/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(OkHttpClient.Builder()
                    .build())
                .build()
        }
    }

    @GET("top/.json")
    fun getTop(): Deferred<Response<RootResponse>>

    @GET
    fun getDetails(@Url url: String): Deferred<Response<List<PostDetail>>>
}