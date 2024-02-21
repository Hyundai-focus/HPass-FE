package com.hyundai.hpass.myPage.model

data class Subscription(
    val memberNo: Int,
    val payment: String,
    val subsNo: Int,
    val subsStartDt: List<Int>
)