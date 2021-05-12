package com.example.foodapp.bindingAdapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.foodapp.R
import org.jsoup.Jsoup

class item_binding_adapter {
    companion object{

        @BindingAdapter("htmlParce")
        @JvmStatic
        fun parcehtmlTOtext(textView: TextView,string: String?){
            if (string!=null){
                var stringParce=Jsoup.parse(string).text()
                textView.text=stringParce
            }
        }
        @BindingAdapter("setNumberOfLike")
        @JvmStatic
        fun setnumberoflike(textView: TextView,likenumber:Int){
            textView.text=likenumber.toString()
        }



        @BindingAdapter("setTimetoCook")
        @JvmStatic
        fun setTimetocook(textView: TextView,timeTocook:Int){
            textView.text=timeTocook.toString()
        }
        @BindingAdapter("setcolorvegan")
        @JvmStatic
        fun setcolorofeco(view: View,vegan:Boolean){
            when{
                view is TextView -> {
                    if (vegan == true){
                        view.setTextColor(ContextCompat.getColor(view.context, R.color.green))
                    }
                }
                view is ImageView ->{
                    if (vegan == true){
                        view.setColorFilter(ContextCompat.getColor(view.context, R.color.green))
                    }

                }
            }
        }

        @BindingAdapter("setimageoffood")
        @JvmStatic
        fun setimageoffood(imageview: ImageView,url:String){
            imageview.load(url){
                crossfade(500)
                error(R.drawable.ic_palceholder)
            }
        }

    }
}