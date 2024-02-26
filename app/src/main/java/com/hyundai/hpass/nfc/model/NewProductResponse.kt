package com.hyundai.hpass.nfc.model

import com.google.gson.annotations.SerializedName

data class NewProductResponse(
    @SerializedName("product_history_no") val productHistoryNo : Long,
    @SerializedName("member_no") val memberNo : Long,
    @SerializedName("product_history_dt") val productHistoryDt : String,
    @SerializedName("product_status") val productReceiveStatus : String,
    @SerializedName("product_no") val productNo : Long,
    @SerializedName("product_brand") val productBrand : String,
    @SerializedName("product_name") val productName : String,
    @SerializedName("product_img") val productImg : String,
    @SerializedName("stock") val stock : Int,
    @SerializedName("receive_dt") val receiveDt : String,
    @SerializedName("receive_loc") val receiveLoc : String,
)
