package com.example.foodapp.bindingAdapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.adapter.Favorite_adapter
import com.example.foodapp.data.database.entity.FavoriteEntity

class Favoritebinding {
    companion object {
        @BindingAdapter("viewvisibility","setdata",requireAll = false)
        @JvmStatic
        fun checkdata_favorite(
            view: View,
            favoriteEntity: List<FavoriteEntity>?,
            adapter: Favorite_adapter?
        ) {
            if (favoriteEntity.isNullOrEmpty()) {
                when (view) {
                    is ImageView -> {
                        view.visibility = View.VISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.VISIBLE
                    }
                    is RecyclerView->
                    {
                        view.visibility=View.INVISIBLE
                    }
                }
            }
            else{
                when (view) {
                    is ImageView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is RecyclerView->
                    {
                        view.visibility=View.VISIBLE
                        adapter?.setdata(favoriteEntity)
                    }
                }
            }

        }
    }
}