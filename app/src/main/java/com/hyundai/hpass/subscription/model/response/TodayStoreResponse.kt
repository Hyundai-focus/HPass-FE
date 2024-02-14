package com.hyundai.hpass.subscription.model.response

import com.google.gson.annotations.SerializedName

data class TodayStoreResponse (
    @SerializedName("store_brand") val name: String,
    @SerializedName("store_loc") val location: String,
    @SerializedName("store_floor") val floor: String,
    @SerializedName("store_img") val image: String
)

