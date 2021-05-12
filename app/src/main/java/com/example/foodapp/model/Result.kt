package com.example.foodapp.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Result(
    @SerializedName("aggregateLikes")
    val aggregateLikes: Int?,
    //@SerializedName("analyzedInstructions")
  //  val analyzedInstructions: @RawValue List<AnalyzedInstruction>,
    @SerializedName("cheap")
    val cheap: Boolean?,
    @SerializedName("creditsText")
    val creditsText: String?,
  //  @SerializedName("cuisines")
   // val cuisines: @RawValue List<String>,
    @SerializedName("dairyFree")
    val dairyFree: Boolean?,
    //@SerializedName("diets")
    //val diets:@RawValue List<String>,
   // @SerializedName("dishTypes")
  //  val dishTypes:@RawValue List<String>,
    @SerializedName("extendedIngredients")
    val extendedIngredients:@RawValue List<ExtendedIngredient>?,
    @SerializedName("gaps")
    val gaps: String?,
    @SerializedName("glutenFree")
    val glutenFree: Boolean?,
    @SerializedName("healthScore")
    val healthScore: Double?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("imageType")
    val imageType: String?,
    @SerializedName("license")
    val license: String?,
    @SerializedName("likes")
    val likes: Int?,
    @SerializedName("lowFodmap")
    val lowFodmap: Boolean?,
    @SerializedName("missedIngredientCount")
    val missedIngredientCount: Int?,
    //@SerializedName("missedIngredients")
   // val missedIngredients:@RawValue List<MissedIngredient>,
   // @SerializedName("nutrition")
  //  val nutrition: @RawValue Nutrition,
    @SerializedName("occasions")
    val occasions: @RawValue List<Any>?,
    @SerializedName("pricePerServing")
    val pricePerServing: Double?,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int?,
    @SerializedName("servings")
    val servings: Int?,
    @SerializedName("sourceName")
    val sourceName: String?,
    @SerializedName("sourceUrl")
    val sourceUrl: String?,
    @SerializedName("spoonacularScore")
    val spoonacularScore: Double?,
    @SerializedName("spoonacularSourceUrl")
    val spoonacularSourceUrl: String?,
    @SerializedName("summary")
    val summary: String?,
    @SerializedName("sustainable")
    val sustainable: Boolean?,
    @SerializedName("title")
    val title: String?,
  //  @SerializedName("unusedIngredients")
    //val unusedIngredients:@RawValue List<Any>,
    @SerializedName("usedIngredientCount")
    val usedIngredientCount: Int?,
   // @SerializedName("usedIngredients")
  //  val usedIngredients:@RawValue List<Any>,
    @SerializedName("vegan")
    val vegan: Boolean?,
    @SerializedName("vegetarian")
    val vegetarian: Boolean?,
    @SerializedName("veryHealthy")
    val veryHealthy: Boolean?,
    @SerializedName("veryPopular")
    val veryPopular: Boolean?,
    @SerializedName("weightWatcherSmartPoints")
    val weightWatcherSmartPoints: Int?
):Parcelable