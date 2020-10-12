package com.zmnload.trucker.network

import com.zmnload.trucker.network.serializer.BaseResponse
import retrofit2.Call

interface OnNetworkResponse {
    fun onSuccess(
        response: BaseResponse?,
        tag: Any?
    )

    fun onFailure(
        response: BaseResponse?,
        tag: Any?
    ) //default void statusCode(Call call, Response response, Object tag , int statusCode){}
}