package com.punksta.apps.openrecycle.model


import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Scheduler

/**
 * Created by stanislav on 3/12/17.
 */
object ApiFactory {
    fun getApi(serverUrl: String, scheduler: Scheduler, gson: Gson): ApiInterface {
        val retrofit = Retrofit.Builder()
                .baseUrl(serverUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(scheduler))
                .build()

        return retrofit.create(ApiInterface::class.java)
    }
}