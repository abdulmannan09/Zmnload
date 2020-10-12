package com.zmnload.trucker.app

import com.zmnload.trucker.network.Network
import com.zmnload.trucker.respository.DataRepository
import com.zmnload.trucker.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    single { DataRepository(get()) }
    single { Network() }
    viewModel { LoginViewModel(get()) }

}

