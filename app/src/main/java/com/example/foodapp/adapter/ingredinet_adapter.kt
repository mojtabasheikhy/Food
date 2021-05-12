package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodapp.R
import com.example.foodapp.model.ExtendedIngredient
import com.example.foodapp.utils.Const_var
import com.example.foodapp.utils.recipes_diff_util
import kotlinx.android.synthetic.main.item_ingredient.view.*

class ingredinet_adapter() :
    RecyclerView.Adapter<ingredinet_adapter.ingredinetViewholder>() {
    var list = emptyList<ExtendedIngredient>()
    inner class ingredinetViewholder(item: View) : RecyclerView.ViewHolder(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ingredinetViewholder {
        return ingredinetViewholder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ingredinetViewholder, position: Int) {
        holder.itemView.ingredient_iv.load(Const_var.baseurl_ingredient + list[position].image){
            crossfade(500)
            error(R.drawable.ic_palceholder)
                .placeholder(R.drawable.ic_baseline_cloud_download_24)
        }
        holder.itemView.ingredient_name.text = list[position].name
        holder.itemView.ingredient_amount.text = list[position].amount.toString()
        holder.itemView.ingredient_unit.text = list[position].unit.toString()
        holder.itemView.ingredient_consisenty.text = list[position].consistency.toString()
        holder.itemView.ingredient_orginal.text = list[position].original

    }

    override fun getItemCount(): Int = list.size

    fun setData(newlistingredinet: List<ExtendedIngredient>) {
        var diffUtil = recipes_diff_util(newlistingredinet, list)
        var diffutil_calucate = DiffUtil.calculateDiff(diffUtil)
        list = newlistingredinet
        diffutil_calucate.dispatchUpdatesTo(this)
    }
}