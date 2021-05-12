package com.example.foodapp.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Length(
    @SerializedName("number")
    val number: Int,
    @SerializedName("unit")
    val unit: String
):Parcelable