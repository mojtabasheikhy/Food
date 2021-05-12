package com.example.foodapp.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class detail_adapter(
   var  result_recived: Bundle,
   var  list_frg:ArrayList<Fragment>,
    frgamentactivity:FragmentActivity
):FragmentStateAdapter(frgamentactivity) {
    override fun getItemCount(): Int {
     return   list_frg.size
    }

    override fun createFragment(position: Int): Fragment {

        list_frg[position].arguments = result_recived
        return list_frg[position]
    }


}