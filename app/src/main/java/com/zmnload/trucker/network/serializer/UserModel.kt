package com.zmnload.trucker.network.serializer

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserModel {

    @SerializedName("first_name")
    @Expose
    var firstName: String? = null

    @SerializedName("last_name")
    @Expose
    var lastName: String? = null

    @SerializedName("dob")
    @Expose
    var dob: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String = ""

    @SerializedName("otp_code")
    @Expose
    var otpCode: Any? = null

    @SerializedName("user_type")
    @Expose
    var userType: Int? = null

    @SerializedName("user_id")
    @Expose
    var userID: Int? = null

    @SerializedName("doctor_type")
    @Expose
    var doctorType: String? = null

    @SerializedName("doctor_fee")
    @Expose
    var doctorFee: String? = null

    @SerializedName("speciality_id")
    @Expose
    var speciality: Int? = null

    @SerializedName("is_enable")
    @Expose
    var isEnable: Boolean? = null

    @SerializedName("profile_image")
    @Expose
    var profileImage: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

    @SerializedName("device_token")
    @Expose
    var deviceToken: String? = null

    @SerializedName("device_type")
    @Expose
    var deviceType: String? = null

    @SerializedName("language")
    @Expose
    var language: String? = null

    @SerializedName("gender")
    @Expose
    var gender: String? = null


    // doctor

    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("speciality_title")
    @Expose
    val specialityTitle: String? = null

    @SerializedName("address_title")
    @Expose
    val addressTitle: String? = null

    @SerializedName("latitude")
    @Expose
    val latitude: String? = null

    @SerializedName("longitude")
    @Expose
    val longitude: String? = null

    @SerializedName("week_days")
    @Expose
    val weekDays: List<Int>? = null
}