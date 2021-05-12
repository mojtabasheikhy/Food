package com.example.foodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.databinding.ItemShimmerRecyclerBinding
import com.example.foodapp.model.Result
import com.example.foodapp.model.foodapi
import com.example.foodapp.utils.recipes_diff_util

class recipes_adapter(var context:Context) : RecyclerView.Adapter<recipes_adapter.recipes_viewholder>() {
    var recipes = emptyList<Result>()

    class recipes_viewholder(var item: ItemShimmerRecyclerBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(result: Result) {
            item.response = result
            item.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recipes_viewholder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: recipes_viewholder, position: Int) {
        holder.bind(recipes[position])
        holder.itemView.animation=AnimationUtils.loadAnimation(context, R.anim.item_animator)
    }

    override fun getItemCount(): Int = recipes.size

    companion object {
        fun from(parent: ViewGroup): recipes_viewholder {
            val view = LayoutInflater.from(parent.context)
            val binding = ItemShimmerRecyclerBinding.inflate(view, parent, false)
            return recipes_viewholder(binding)
        }
    }

    fun setdata(newdata: foodapi) {
        var difutils_obj = recipes_diff_util(newdata.results, recipes)
        var difutils_calucate = DiffUtil.calculateDiff(difutils_obj)
        recipes = newdata.results
        difutils_calucate.dispatchUpdatesTo(this)

    }

}