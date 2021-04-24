package com.ranshiroirie.felicaserverconnect.felica.device

import com.ranshiroirie.felicaserverconnect.MyApplication
import com.ranshiroirie.felicaserverconnect.R
import com.ranshiroirie.felicaserverconnect.util.DateJsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

object RestUtil {
    private var url_end_point = "http:/${MyApplication.instance.getString(R.string.api_client_url)}:8080/"
    val retrofit: Retrofit

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val moshi = Moshi.Builder().add(Date::class.java, DateJsonAdapter()).build()
        val httpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val builder = Retrofit.Builder()
            .baseUrl(url_end_point)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)

        retrofit = builder.build()
    }
}