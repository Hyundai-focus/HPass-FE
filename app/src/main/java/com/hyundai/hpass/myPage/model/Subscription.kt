package com.hyundai.hpass.myPage.model

data class Subscription(
    val memberNo: Int,
    val payment: String,
    val subsNo: Int,
    val lastDate: String,
    val subsStartDt: List<Int>
)