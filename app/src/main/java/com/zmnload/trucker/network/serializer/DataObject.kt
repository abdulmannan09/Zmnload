package com.zmnload.trucker.network.serializer

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DataObject :Serializable{
    @SerializedName("doctor_profile")
    @Expose
    val doctorProfile: UserModel? = null
    @SerializedName("user")
    @Expose
    val userModel: UserModel? = null
}