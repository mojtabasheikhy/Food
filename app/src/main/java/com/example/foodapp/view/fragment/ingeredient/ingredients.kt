package com.example.foodapp.view.fragment.ingeredient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.adapter.ingredinet_adapter
import com.example.foodapp.model.Result
import com.example.foodapp.utils.Const_var
import kotlinx.android.synthetic.main.fragment_ingredients.view.*


class ingredients : Fragment() {

    val ingre_adapter:ingredinet_adapter by lazy { ingredinet_adapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       var view= inflater.inflate(R.layout.fragment_ingredients, container, false)

       var budel=arguments?.getParcelable<Result>(Const_var.detail_overview_bundel_key)
        budel?.extendedIngredients?.let {
            ingre_adapter.setData(it)
        }

       setuprecycelerview(view)


        return view
    }
    fun setuprecycelerview(view: View){
        view.ingredient_recycler.adapter = ingre_adapter
        view.ingredient_recycler.layoutManager=LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)

    }

}