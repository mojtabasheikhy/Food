package com.example.foodapp.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Nutrition(
    @SerializedName("caloricBreakdown")
    val caloricBreakdown:@RawValue CaloricBreakdown,
    @SerializedName("flavanoids")
    val flavanoids:@RawValue List<Flavanoid>,
    @SerializedName("ingredients")
    val ingredients: @RawValue List<IngredientX>,
    @SerializedName("nutrients")
    val nutrients:@RawValue List<NutrientX>,
    @SerializedName("properties")
    val properties: @RawValue List<Property>,
    @SerializedName("weightPerServing")
    val weightPerServing:@RawValue WeightPerServing
):Parcelable