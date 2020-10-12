package com.zmnload.trucker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zmnload.trucker.network.OnNetworkResponse
import com.zmnload.trucker.network.serializer.BaseResponse
import com.zmnload.trucker.network.serializer.UserModel
import com.zmnload.trucker.respository.DataRepository
import org.koin.core.KoinComponent

class LoginViewModel(val dataRepository: DataRepository) : ViewModel(), KoinComponent {

    var loginData = MutableLiveData<BaseResponse>()

    init {
        loginData.value = BaseResponse()
    }

    fun getLoginData(
        userModel: UserModel,
        tag: Int,
        callback: OnNetworkResponse
    ) {
        dataRepository.callApi(dataRepository.network.apis()!!.loginApi(userModel), tag, callback)
    }

    fun clear() {
        dataRepository.compositeDisposable.clear()
    }
}