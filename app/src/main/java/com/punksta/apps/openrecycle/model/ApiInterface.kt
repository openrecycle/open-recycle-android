package com.punksta.apps.openrecycle.model

import com.punksta.apps.openrecycle.entity.ClassificationResult
import com.punksta.apps.openrecycle.entity.Response
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import rx.Single


/**
 * Created by stanislav on 3/11/17.
 */
interface ApiInterface {
    @Multipart
    @PUT("/api/UploadFile4Recognition")
    fun classify(@Part file: MultipartBody.Part,
                 @Part("filename") fileName: String,
                 @Part("user_id") userId: String,
                 @Part("source") source: String): Single<ClassificationResult>

    @Multipart
    @PUT("/api/UploadFile4Learning")
    fun uploadMarkedData(@Part file: MultipartBody.Part,
                         @Part("filename") fileName: String,
                         @Part("descr") fileDescription: String,
                         @Part("user_id") userId: String,
                         @Part("source") source: String): Single<Response>
}