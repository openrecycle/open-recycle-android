package com.punksta.apps.openrecycle.model

import com.punksta.apps.openrecycle.entity.ClassificationResult
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import rx.Single


/**
 * Created by stanislav on 3/11/17.
 */
interface ApiInterface {
    @Multipart
    @POST("rest/api/v1/classify")
    fun classify(@Part file: MultipartBody.Part): Single<Response<ClassificationResult>>

    @Multipart
    @POST("rest/api/v1/labeling_data")
    fun labelData(@Part file: MultipartBody.Part): Single<Response<ClassificationResult>>
}