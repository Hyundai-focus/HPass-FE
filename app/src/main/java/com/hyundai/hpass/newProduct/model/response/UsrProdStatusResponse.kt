package com.hyundai.hpass.newProduct.model.response

/**
 *
 * @author 김은서
 *
 */

data class UsrProdStatusResponse(
    val status : Boolean,
    val memberName : String,
    val prodImg : String,
    val prodName : String,
    val prodBrand : String,
    val receiveDt : String,
    val receiveLoc : String,
)