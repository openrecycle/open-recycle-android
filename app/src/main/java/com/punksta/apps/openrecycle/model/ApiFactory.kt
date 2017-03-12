package com.punksta.apps.openrecycle.model


import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Scheduler

/**
 * Created by stanislav on 3/12/17.
 */
object ApiFactory {
    fun getApi(serverUrl: String,
               scheduler: Scheduler,
               gson: Gson,
               okHttpClient: OkHttpClient
    ): ApiInterface {
        val retrofit = Retrofit.Builder()
                .baseUrl(serverUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(scheduler))
                .build()

        return retrofit.create(ApiInterface::class.java)
    }
}