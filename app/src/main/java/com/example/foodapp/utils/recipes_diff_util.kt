package com.example.foodapp.utils

import androidx.recyclerview.widget.DiffUtil

class recipes_diff_util<T>(var newlist:List<T>,var oldlist:List<T>):DiffUtil.Callback() {
    override fun getOldListSize(): Int =oldlist.size

    override fun getNewListSize(): Int =newlist.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return oldlist[oldItemPosition] === newlist[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlist[oldItemPosition] == newlist[newItemPosition]
    }
}