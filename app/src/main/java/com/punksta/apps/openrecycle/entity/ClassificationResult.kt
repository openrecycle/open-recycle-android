package com.punksta.apps.openrecycle.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by stanislav on 3/11/17.
 */
data class ClassificationResult(@SerializedName("Message") var type: String = "неизвестный тип")