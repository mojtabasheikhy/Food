package com.example.foodapp.bindingAdapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.foodapp.data.database.entity.recipes_entity
import com.example.foodapp.model.Result
import com.example.foodapp.model.foodapi
import com.example.foodapp.utils.NetworkResualt
import com.example.foodapp.view.recipes_frgDirections
import com.google.android.material.card.MaterialCardView

class recipes_main_binding {
    companion object {

        @BindingAdapter("setonclick")
        @JvmStatic
        fun onrecipes_clicklistener(coordinatorLayout: MaterialCardView,result: Result,)
        {
            coordinatorLayout.setOnClickListener{
                try {
                    Log.e("Sd","ASd")

                    var action_recipes_to_detail=recipes_frgDirections.actionRecipesFrgToDetailActivity(result)
                    coordinatorLayout.findNavController().navigate(action_recipes_to_detail)
                }
                catch (e:Exception){
                    Log.e("t1",e.toString())
                }
            }

        }

        @BindingAdapter("ReadApiResponseImage","ReadDataBaseImage",requireAll = true)
        @JvmStatic
        fun error_load_image(
            imageView: ImageView, networkResualt: NetworkResualt<foodapi>?,
            ListDataBase: List<recipes_entity>?
        ) {
            if (networkResualt is NetworkResualt.Error && ListDataBase.isNullOrEmpty()){
                imageView.visibility=View.VISIBLE
            }
            else if(networkResualt is NetworkResualt.loading ){
                imageView.visibility=View.INVISIBLE
            }
            else if (networkResualt is NetworkResualt.success){
                imageView.visibility=View.INVISIBLE
            }

        }
        @BindingAdapter("ReadApiResponseTextView","ReadDataBaseTextView",requireAll = true)
        @JvmStatic
        fun error_load_textview(
            textview: TextView, networkResualt: NetworkResualt<foodapi>?,
            ListDataBase: List<recipes_entity>?
        ) {
            if (networkResualt is NetworkResualt.Error && ListDataBase.isNullOrEmpty()){
                textview.visibility=View.VISIBLE
                textview.text =networkResualt.message.toString()
            }
            else if(networkResualt is NetworkResualt.loading ){
                textview.visibility=View.INVISIBLE
            }
            else if (networkResualt is NetworkResualt.success){
                textview.visibility=View.INVISIBLE
            }

        }

    }

}