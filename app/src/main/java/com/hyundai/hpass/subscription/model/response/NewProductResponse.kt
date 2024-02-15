package com.hyundai.hpass.subscription.model.response

import com.google.gson.annotations.SerializedName

data class NewProductResponse (
    @SerializedName("product_brand") val brand: String,
    @SerializedName("product_name") val name: String,
    @SerializedName("product_img") val image: String,
    @SerializedName("stock") val stock: Int,
    @SerializedName("receive_dt") val receiveDate: String,
    @SerializedName("receive_loc") val receiveLocation: String
)
