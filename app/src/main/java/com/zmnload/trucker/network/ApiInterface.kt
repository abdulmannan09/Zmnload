package com.zmnload.trucker.network


import com.zmnload.trucker.network.serializer.BaseResponse
import com.zmnload.trucker.network.serializer.UserModel
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiInterface {

    companion object {
        const val LOGIN: String = "login"
    }

    @POST(LOGIN)
    fun loginApi(@Body userModel: UserModel): Observable<BaseResponse>

}