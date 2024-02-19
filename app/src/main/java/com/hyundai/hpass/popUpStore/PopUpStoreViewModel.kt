package com.hyundai.hpass.popUpStore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hyundai.hpass.subscription.model.response.PopUpStoreResponse

/**
 *
 * @author 김기훈
 *
 */
class PopUpStoreViewModel: ViewModel() {

    private val popUpStore: MutableLiveData<List<PopUpStoreResponse>> = MutableLiveData()

    init {
        loadPopUpStore()
        mockData()
    }

    fun getPopUpStore(): MutableLiveData<List<PopUpStoreResponse>> = popUpStore

    private fun loadPopUpStore() {

    }

    private fun mockData() {
        popUpStore.value = listOf(
            PopUpStoreResponse(1, "스타벅스", "02.01(목)", "02.07(수)", "1층", "https://www.ehyundai.com/attachfiles/branch/20210209104434346.jpg"),
            PopUpStoreResponse(2,"이디야", "02.01(목)", "02.07(수)", "2층", "https://www.ehyundai.com/attachfiles/branch/20220906035951614.jpg"),
            PopUpStoreResponse(3,"투썸플레이스", "02.1(목)", "02.07(수)", "3층", "https://www.ehyundai.com/attachfiles/branch/20210209093808991.jpg")
        )
    }
}