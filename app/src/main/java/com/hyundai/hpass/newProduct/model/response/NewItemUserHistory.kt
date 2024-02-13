package com.hyundai.hpass.newProduct.model.response

data class NewItemUserHistory(
    val status : Boolean,
    val userName : String,
    val title : String,
    val itemName : String,
    val itemImg : String,
    val time : String,
    val place :String
 )
