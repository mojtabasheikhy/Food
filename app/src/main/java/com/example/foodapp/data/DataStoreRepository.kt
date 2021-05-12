package com.example.foodapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodapp.utils.Const_var
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {
    object prefKey {
        var SelectMealType = stringPreferencesKey(Const_var.pref_key_MealType)
        var SelectMealType_id = intPreferencesKey(Const_var.pref_key_MealType_id)
        var SelectDietType = stringPreferencesKey(Const_var.pref_key_DietType)
        var SelectDietType_id = intPreferencesKey(Const_var.pref_key_DietType_id)
        var backonline = booleanPreferencesKey(Const_var.backonline)
    }


    val Context.mydatastore: DataStore<Preferences> by preferencesDataStore(name = Const_var.pref_DataStore_Name)
    suspend fun savedata_bottomeshet_meal_diet(
        selected_Meal_type: String,
        selected_meal_type_id: Int,
        selected_Diet_type: String,
        selected_Diet_type_id: Int,
    ) {
        context.mydatastore.edit { pref ->
            pref[prefKey.SelectMealType] = selected_Meal_type
            pref[prefKey.SelectMealType_id] = selected_meal_type_id
            pref[prefKey.SelectDietType] = selected_Diet_type
            pref[prefKey.SelectDietType_id] = selected_Diet_type_id


        }
    }


    suspend fun savebackonline(backonline: Boolean) {
        context.mydatastore.edit { pref -> pref[prefKey.backonline] = backonline
        }
    }


    val read_data_bs_type: Flow<Meal_diet_type> = context.mydatastore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }
        .map { pref ->
            val meal_data_type =
                pref[DataStoreRepository.prefKey.SelectMealType] ?: Const_var.default_mealType
            val meal_data_type_id = pref[prefKey.SelectMealType_id] ?: 0
            val diet_data_type = pref[prefKey.SelectDietType] ?: Const_var.default_dietType
            val diet_data_type_id = pref[prefKey.SelectDietType_id] ?: 0
            Meal_diet_type(meal_data_type, meal_data_type_id, diet_data_type, diet_data_type_id)
        }

    var readbackonline: Flow<Boolean> = context.mydatastore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { pref ->
        val backonline=pref[prefKey.backonline] ?:false
        backonline
    }

}


data class Meal_diet_type(
    var selectedMeal_type: String,
    var selectedMeal_type_id: Int,
    var selectedDiet_type: String,
    var selectedDiet_type_id: Int,
)