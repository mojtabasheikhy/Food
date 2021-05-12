package com.example.foodapp.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeightPerServing(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("unit")
    val unit: String
):Parcelable