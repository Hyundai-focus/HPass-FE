package com.hyundai.hpass.subscription

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hyundai.hpass.subscription.model.response.NewProductResponse
import com.hyundai.hpass.subscription.model.response.PopUpStoreResponse
import com.hyundai.hpass.subscription.model.response.TodayStoreResponse
import com.hyundai.hpass.subscription.model.response.UserResponse

/**
 *
 * @author 김기훈
 *
 */
class SubscriptionMainViewModel: ViewModel() {
    private val user: MutableLiveData<UserResponse> = MutableLiveData()
    private val popUpStore: MutableLiveData<List<PopUpStoreResponse>> = MutableLiveData()
    private val todayStore: MutableLiveData<List<TodayStoreResponse>> = MutableLiveData()
    private val newProduct: MutableLiveData<List<NewProductResponse>> = MutableLiveData()

    init {
        loadUser()
        loadPopUpStore()
        loadTodayStore()
        loadNewProduct()
        mockData()
    }

    fun getUser(): MutableLiveData<UserResponse> = user
    fun getPopUpStore(): MutableLiveData<List<PopUpStoreResponse>> = popUpStore
    fun getTodayStore(): MutableLiveData<List<TodayStoreResponse>> = todayStore
    fun getNewProduct(): MutableLiveData<List<NewProductResponse>> = newProduct

    private fun loadUser() {

    }

    private fun loadPopUpStore() {

    }

    private fun loadTodayStore() {

    }

    private fun loadNewProduct() {

    }

    private fun mockData() {
        user.value = UserResponse("김기훈", "", "", "")

        popUpStore.value = listOf(
            PopUpStoreResponse("스타벅스", "", "", "", "https://www.ehyundai.com/attachfiles/branch/20210209104434346.jpg"),
            PopUpStoreResponse("이디야", "", "", "", "https://www.ehyundai.com/attachfiles/branch/20220906035951614.jpg"),
            PopUpStoreResponse("투썸플레이스", "", "", "", "https://www.ehyundai.com/attachfiles/branch/20210209093808991.jpg")
        )

        todayStore.value = listOf(
            TodayStoreResponse("스타벅스", "강남점", "1층", "https://www.ehyundai.com/attachfiles/branch/20220906023132877.jpg"),
            TodayStoreResponse("이디야", "강남점", "2층", "https://www.ehyundai.com/attachfiles/branch/20220906015048178.JPG"),
            TodayStoreResponse("투썸플레이스", "강남점", "3층", "https://www.ehyundai.com/attachfiles/branch/20210209093437412.jpg")
        )

        newProduct.value = listOf(
            NewProductResponse("스타벅스", "강남점", "https://www.ehyundai.com/attachfiles/branch/20220906114329344.jpg", 11, "", ""),
            NewProductResponse("이디야", "강남점", "https://www.ehyundai.com/attachfiles/branch/20230922061618400.jpg", 22, "",""),
            NewProductResponse("투썸플레이스", "강남점", "https://www.ehyundai.com/attachfiles/branch/20220906023403755.jpg", 33, "", "")
        )
    }
}