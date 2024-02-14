package com.hyundai.hpass.subscription.model.response

import com.google.gson.annotations.SerializedName

/**
 *
 * @author 김기훈
 *
 */
data class PopUpStoreResponse (
    @SerializedName("popup_name") val name: String,
    @SerializedName("popup_start_dt") val startDate: String,
    @SerializedName("popup_end_dt") val endDate: String,
    @SerializedName("popup_loc") val location: String,
    @SerializedName("popup_img") val image: String
)