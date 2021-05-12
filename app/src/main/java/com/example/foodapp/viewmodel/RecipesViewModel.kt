package com.example.foodapp.viewmodel

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.DataStoreRepository
import com.example.foodapp.utils.Const_var
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    var dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    var mealtype = Const_var.default_mealType
    var dietType = Const_var.default_dietType

    var backOnline = false
    var status_network = false

    var read_Data_bs_selected = dataStoreRepository.read_data_bs_type
    var  readbackonline=dataStoreRepository.readbackonline.asLiveData()
    fun save_data_bs_selected(
        mealtype: String,
        mealtype_id: Int,
        dietType: String,
        dietType_Id: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.savedata_bottomeshet_meal_diet(
            mealtype,
            mealtype_id,
            dietType,
            dietType_Id
        )
    }
    fun savebackonline(backonline:Boolean){
        viewModelScope.launch (Dispatchers.IO){
            dataStoreRepository.savebackonline(backonline)
        }
    }


    fun applyhashmap(): HashMap<String, String> {
        viewModelScope.launch {
            read_Data_bs_selected.collect {
                mealtype = it.selectedMeal_type
                dietType = it.selectedDiet_type
            }
        }


        var queryies: HashMap<String, String> = HashMap()
        queryies["number"] = "50"
        queryies["apiKey"] = Const_var.apikey
        queryies[Const_var.addRecipeInformation] = "true"
        queryies[Const_var.fillIngredients] = "true"
        queryies["type"] = mealtype
        queryies["diet"] = dietType
        queryies["defualtnumber"] = Const_var.default_number
        return queryies


    }

    fun apply_search_hashmap(query_search:String):Map<String,String>{
      var query:HashMap<String,String>  = java.util.HashMap()
        query[Const_var.search_query] = query_search
        query["number"]=Const_var.default_number
        query["apiKey"]=Const_var.apikey
        query[Const_var.addRecipeInformation] = "true"
        query[Const_var.fillIngredients] = "true"
        return query
    }

    fun toast_state_status(view: View) {
        if (!status_network) {
            Toast.makeText(getApplication(), "No Network Access", Toast.LENGTH_SHORT).show()
            savebackonline(true)
            //  Snackbar.make(getApplication(),view,"No Network connection",Snackbar.LENGTH_SHORT).show()
        }
        else if(status_network){
            if (backOnline){
                Toast.makeText(getApplication(), "You are backonline", Toast.LENGTH_SHORT).show()
                savebackonline(false)
            }
        }

    }
}