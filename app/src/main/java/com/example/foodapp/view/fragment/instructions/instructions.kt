package com.example.foodapp.view.fragment.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.foodapp.R
import com.example.foodapp.model.Result
import com.example.foodapp.utils.Const_var
import kotlinx.android.synthetic.main.fragment_instructions.view.*


class instructions : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      var view= inflater.inflate(R.layout.fragment_instructions, container, false)
        view.instruction_wbview.webViewClient = object :WebViewClient(){}
        arguments?.getParcelable<Result>(Const_var.detail_overview_bundel_key)!!.sourceUrl?.let {
            view.instruction_wbview.loadUrl(
                it
            )
        }
        return view
    }

}