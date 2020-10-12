package com.zmnload.trucker.network.serializer

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BaseResponse : Serializable {
    @SerializedName("success")
    @Expose
    var isSuccess = false

    @SerializedName("status_code")
    @Expose
    var code = 0

    @SerializedName("message")
    @Expose
    var message: String = ""

    @SerializedName("data")
    @Expose
    var dataObject: DataObject = DataObject()
}