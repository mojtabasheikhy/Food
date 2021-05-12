package com.example.foodapp.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentJokeFrgBinding
import com.example.foodapp.utils.Const_var
import com.example.foodapp.utils.NetworkResualt
import com.example.foodapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class joke_frg : Fragment() {
    var foodjoke = "no joke"
    val mainViewModel by viewModels<MainViewModel>()
    var bind:FragmentJokeFrgBinding?=null
    val _bind get() = bind
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       bind= FragmentJokeFrgBinding.inflate(inflater,container,false)
        _bind?.lifecycleOwner=viewLifecycleOwner
        setHasOptionsMenu(true)
        bind!!.foodjoke=mainViewModel
        mainViewModel.joke_api(Const_var.apikey)
        mainViewModel.mld_getjoke.observe(viewLifecycleOwner,{
            when(it)
            {
                is NetworkResualt.success -> {
                    bind!!.jokeErrorIv.visibility=View.INVISIBLE
                    bind!!.jokeErrorTv.visibility=View.INVISIBLE
                    bind!!.jokeProgressBar.visibility=View.INVISIBLE
                    bind!!.jokeTv.text=it.data?.text
                    Toast.makeText(requireContext(), it!!.data!!.text,Toast.LENGTH_SHORT).show()
                     foodjoke = it!!.data!!.text

                 }
                is NetworkResualt.loading -> {
                    bind!!.jokeErrorIv.visibility=View.INVISIBLE
                    bind!!.jokeErrorTv.visibility=View.INVISIBLE
                    bind!!.jokeProgressBar.visibility=View.VISIBLE

                }
                is NetworkResualt.Error ->{
                    loadfrom_catch_joke()

                }
            }
        })

        return bind!!.root

    }
    fun loadfrom_catch_joke(){
        lifecycleScope.launch{
            bind!!.jokeProgressBar.visibility=View.INVISIBLE
            mainViewModel.mld_read_localjoke.observe(viewLifecycleOwner,{
                if (it.isNotEmpty()&&it!=null){
                    bind?.jokeTv?.text = it[0].text
                    foodjoke= it[0].text
                }
                else{
                    bind!!.jokeErrorIv.visibility=View.VISIBLE
                    bind!!.jokeErrorTv.visibility=View.VISIBLE
                    bind!!.jokeMaterialCardView.visibility=View.INVISIBLE
                }
            })
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        bind=null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sharemenu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.sharemenu){
            var intent2=Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT,foodjoke)
                this.type="text/plain"
            }
            startActivity(intent2)

        }
        return super.onOptionsItemSelected(item)
    }


}