package com.example.foodapp.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Step(
    @SerializedName("equipment")
    val equipment:@RawValue List<Equipment>,
    @SerializedName("ingredients")
    val ingredients:@RawValue List<Ingredient>,
    @SerializedName("length")
    val length:@RawValue Length,
    @SerializedName("number")
    val number: Int,
    @SerializedName("step")
    val step: String
):Parcelable