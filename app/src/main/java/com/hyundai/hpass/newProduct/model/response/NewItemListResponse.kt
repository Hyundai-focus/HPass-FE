package com.hyundai.hpass.newProduct.model.response

import java.io.Serializable

data class NewItemListResponse(
    val title : String,
    val subTitle : String,
    val img : String,
    val status : Boolean
): Serializable