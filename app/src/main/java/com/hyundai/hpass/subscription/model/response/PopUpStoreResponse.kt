package com.hyundai.hpass.subscription.model.response

import com.google.gson.annotations.SerializedName

/**
 *
 * @author 김기훈
 *
 */
data class PopUpStoreResponse (
    @SerializedName("popUpNo") val no: Int,
    @SerializedName("popUpName") val name: String,
    @SerializedName("popUpStartDate") val startDate: String,
    @SerializedName("popUpEndDate") val endDate: String,
    @SerializedName("pupUpLocation") val location: String,
    @SerializedName("popUpImage") val image: String
)