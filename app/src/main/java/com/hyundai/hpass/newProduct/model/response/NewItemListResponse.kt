package com.hyundai.hpass.newProduct.model.response

import java.io.Serializable

data class NewItemListResponse(
    val productNo : Long,
    val productBrand : String,
    val productName : String,
    val productImg : String,
    val stock : Int,
    val receiveDt:String,
    val receiveLoc : String
): Serializable