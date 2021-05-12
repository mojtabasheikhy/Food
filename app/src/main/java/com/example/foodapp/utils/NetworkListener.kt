package com.example.foodapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalCoroutinesApi
class NetworkListener:ConnectivityManager.NetworkCallback (){



    var isNetworkAvailabe=MutableStateFlow(false)

    fun checkNetworkAvailable(context: Context):MutableStateFlow<Boolean>{
        var connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(this)

        var isconncet=false

        connectivityManager.allNetworks.forEach {
            var networkCapabilities=connectivityManager.getNetworkCapabilities(it)
            networkCapabilities?.let {network->
                if (network.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
                    isconncet=true
                    return@forEach
                }
            }
        }

        isNetworkAvailabe.value=isconncet
        return isNetworkAvailabe
    }
    override fun onAvailable(network: Network) {
        isNetworkAvailabe.value=true
    }

    override fun onLost(network: Network) {
        isNetworkAvailabe.value=false
    }
}