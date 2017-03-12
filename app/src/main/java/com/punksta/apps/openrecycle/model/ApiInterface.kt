package com.punksta.apps.openrecycle.model

import com.punksta.apps.openrecycle.entity.Response
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import rx.Single


/**
 * Created by stanislav on 3/11/17.
 */
interface ApiInterface {

    @Multipart
    @POST("/api/UploadFile4Learning")
    fun classify(@Part file: MultipartBody.Part,
                 @Part("finename") fileName: String,
                 @Part("user_id") userId: String,
                 @Part("source") source: String): Single<Response>

    @Multipart
    @POST("/api/UploadFile4Learning")
    fun uploadMarkedData(@Part file: MultipartBody.Part,
                         @Part("finename") fileName: String,
                         @Part("descr") fileDescription: String,
                         @Part("user_id") userId: String,
                         @Part("source") source: String): Single<Response>
}