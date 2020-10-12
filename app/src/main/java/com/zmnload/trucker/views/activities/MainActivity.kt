package com.zmnload.trucker.views.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.zmnload.trucker.R
import com.zmnload.trucker.app.BaseActivity
import com.zmnload.trucker.databinding.ActivityMainBinding
import com.zmnload.trucker.helper.UIHelper
import com.zmnload.trucker.network.OnNetworkResponse
import com.zmnload.trucker.network.serializer.BaseResponse
import com.zmnload.trucker.network.serializer.UserModel
import com.zmnload.trucker.utils.Constants
import com.zmnload.trucker.viewmodel.LoginViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), OnNetworkResponse {

    lateinit var binding: ActivityMainBinding
    val loginViewModel: LoginViewModel by viewModel()
    val uiHelper: UIHelper by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val userModel = UserModel()
        userModel.userType = 2
        userModel.phone = "+966343433434"
        userModel.password = "123456"
        userModel.deviceToken =
            "cRy1mQlrSEKk74MtwuAbYN:APA91bEbNYJU2EHylVzczP69cgGzancYyyHbnuUi2xGtqe3IcdeMF4x5nYDd3EUHmo-AUzrGDa4K_x_x8ZbkyZODSab-Dgse-RQpViYet5l0x53JSz4OP2wKUrhHfopQXO2NPnf2ifRV"
        userModel.deviceType = "Android"
        userModel.language = "en"
        loginViewModel.getLoginData(
            userModel,
            Constants.API.LOGIN,
            this
        )
    }

    override fun onSuccess(response: BaseResponse?, tag: Any?) {
        when (tag) {
            Constants.API.LOGIN -> {
                binding.model = response?.dataObject?.userModel
                uiHelper.showLongToastInCenter(this, response?.message.toString())
            }
        }
    }

    override fun onFailure(response: BaseResponse?, tag: Any?) {
        uiHelper.showLongToastInCenter(this, response?.message.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        loginViewModel.clear()
    }
}