package com.example.foodapp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.foodapp.data.database.entity.FavoriteEntity
import com.example.foodapp.data.database.entity.joke_entity
import com.example.foodapp.data.database.entity.recipes_entity
import com.example.foodapp.data.repository
import com.example.foodapp.model.Joke
import com.example.foodapp.model.foodapi
import com.example.foodapp.utils.NetworkResualt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(var repository: repository, application: Application) : AndroidViewModel(application) {

    /**joke food**/
    var mld_getjoke: MutableLiveData<NetworkResualt<Joke>> = MutableLiveData()
    var mld_read_localjoke: LiveData<List<Joke>> = repository.local.read_joke_local().asLiveData()

    fun insert_joke_toDataBase_local(jokeEntity: joke_entity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insert_joke_local(jokeEntity)
        }
    fun joke_api(apikey:String) = viewModelScope.launch {
        getfoodjokesafecall(apikey)
    }

    private fun set_data_joke_catch(joke: Joke) {
        var joke = joke_entity(joke)
        insert_joke_toDataBase_local(joke)
    }




    private suspend fun getfoodjokesafecall(apikey: String) {
        mld_getjoke.value = NetworkResualt.loading()
        if (checkInternet_connection()) {
            try {
                var response_joke = repository.remote.joke(apikey)
                mld_getjoke.value = handle_joke(response_joke)
                var food_joke=mld_getjoke.value!!.data
                if (food_joke!=null){
                    set_data_joke_catch(food_joke)
                }

            } catch (e: Exception) {
                mld_getjoke.value = NetworkResualt.Error("no data like that ")
                e.printStackTrace()
            }
        } else {

            mld_getjoke.value = NetworkResualt.Error("No Internet Connection")
        }
    }

    private fun handle_joke(responseJoke: Response<Joke>): NetworkResualt<Joke>? {
        when {
            responseJoke.message().contains("timeout") -> {
                return NetworkResualt.Error("time out")
            }
            responseJoke.code() == 402 -> {
                return NetworkResualt.Error("api key limited")
            }
            responseJoke.body()?.text.isNullOrEmpty() -> {
                return NetworkResualt.Error("Recipes Is Null")
            }
            responseJoke.isSuccessful -> {
                val data = responseJoke.body()
                return data?.let { NetworkResualt.success(it) }
            }
            else -> {
                return NetworkResualt.Error(responseJoke.message().toString())
            }
        }
    }

    /**favorite**/
    var mld_read_favorite:LiveData<List<FavoriteEntity>> = repository.local.read_favorite_local().asLiveData()

    fun insert_favorite_toDataBase_local(favoriteEntity: FavoriteEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insert_favorite_local(favoriteEntity)
        }
    fun delete_favorite_toDataBase_local(favoriteEntity: FavoriteEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteonefavorite(favoriteEntity)
        }
    fun deleteall_favorite_toDataBase_local() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteallfavorite()
        }

    /**local fetch data**/
    var mld_read_localdatabase: LiveData<List<recipes_entity>> = repository.local.read_recipes_local().asLiveData()



    fun insert_data_toDataBase_local(localdata: recipes_entity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insert_recipes_local(localdata)
        }

    /**online fetch data**/
    var mld_getdata_recipes_response: MutableLiveData<NetworkResualt<foodapi>> = MutableLiveData()
    var mld_searched_recipes_response:MutableLiveData<NetworkResualt<foodapi>> = MutableLiveData()
    fun getrecipes_online(query: Map<String, String>) = viewModelScope.launch {
        has_safe_call(query)
    }

    fun search_recipes_query(search_query:Map<String,String>)=viewModelScope.launch {
        search_recipes_has_safe_call(search_query)
    }

    private suspend fun search_recipes_has_safe_call(searchQuery: Map<String, String>) {
        mld_searched_recipes_response.value = NetworkResualt.loading()
        if (checkInternet_connection()) {
            try {
                var response_search = repository.remote.search_recipes(searchQuery)
                mld_searched_recipes_response.value = handelfood_recipes_response(response_search)

            } catch (e: Exception) {
                mld_getdata_recipes_response.value = NetworkResualt.Error("no data like that ")
                e.printStackTrace()
            }
        } else {

            mld_getdata_recipes_response.value = NetworkResualt.Error("No Internet Connection")
        }
    }


    suspend fun has_safe_call(query: Map<String, String>) {
        mld_getdata_recipes_response.value = NetworkResualt.loading()
        if (checkInternet_connection()) {

            try {
                var response = repository.remote.get_recipes(query)
                mld_getdata_recipes_response.value = handelfood_recipes_response(response)
                var response_data = mld_getdata_recipes_response.value?.data
                if (response_data != null) {
                    set_data_catch(response_data)
                }
            } catch (e: Exception) {
                mld_getdata_recipes_response.value = NetworkResualt.Error("recipes not found ")
                e.printStackTrace()
            }
        } else {

            mld_getdata_recipes_response.value = NetworkResualt.Error("No Internet Connection")
        }
    }

    private fun set_data_catch(responseData: foodapi) {
        var recipes = recipes_entity(responseData)
        insert_data_toDataBase_local(recipes)
    }

    private fun handelfood_recipes_response(response: Response<foodapi>): NetworkResualt<foodapi>? {
        when {
            response.message().contains("timeout") -> {
                return NetworkResualt.Error("time out")
            }
            response.code() == 402 -> {
                return NetworkResualt.Error("api key limited")
            }
            response.body()?.results.isNullOrEmpty() -> {
                return NetworkResualt.Error("Recipes Is Null")
            }
            response.isSuccessful -> {
                val data = response.body()
                return data?.let { NetworkResualt.success(it) }
            }
            else -> {
                return NetworkResualt.Error(response.message().toString())
            }
        }
    }
    @SuppressLint("WrongConstant")
    fun checkInternet_connection(): Boolean {
        val connect_manager = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val active_network = connect_manager.activeNetwork ?: return false
        val capabiliti = connect_manager.getNetworkCapabilities(active_network) ?: return false
        return when {
            capabiliti.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabiliti.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabiliti.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


}