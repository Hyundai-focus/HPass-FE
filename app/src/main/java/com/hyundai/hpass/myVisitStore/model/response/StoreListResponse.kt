package com.hyundai.hpass.myVisitStore.model.response

import java.io.Serializable

data class StoreListResponse(
    val storeBrand : String,
    val storeImg : String,
    val storeFloor : String,
    val visitStatus: Boolean,
    val storeMap:String
): Serializable
