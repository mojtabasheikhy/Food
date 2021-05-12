package com.example.foodapp.utils

import androidx.lifecycle.ViewModelProvider

sealed class NetworkResualt<T>(var data: T? = null, var message: String? = null) {


    class success<T>(data: T):NetworkResualt<T>(data)
    class Error<T>(message: String?,data: T? = null):NetworkResualt<T>(data,message)
    class loading<T>:NetworkResualt<T>()
}