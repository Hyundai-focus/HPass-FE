package com.hyundai.hpass.subscription.model.response

import com.google.gson.annotations.SerializedName

/**
 *
 * @author 김기훈
 *
 */
data class UserResponse (
    @SerializedName("member_name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("reg_dt") val registerDate: String,
    @SerializedName("refresh_token") val refreshToken: String,
)