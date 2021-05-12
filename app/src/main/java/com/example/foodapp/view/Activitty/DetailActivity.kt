package com.example.foodapp.view.Activitty

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.foodapp.R
import com.example.foodapp.adapter.detail_adapter
import com.example.foodapp.databinding.ActivityDetailBinding
import com.example.foodapp.utils.Const_var
import com.example.foodapp.view.fragment.ingeredient.ingredients
import com.example.foodapp.view.fragment.instructions.instructions
import com.example.foodapp.view.fragment.overview.overview
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    val args by navArgs<DetailActivityArgs>()
    lateinit var bind_detail: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind_detail = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bind_detail.root)

        setSupportActionBar(bind_detail.detailToolbar)
        bind_detail.detailToolbar.setTitleTextColor(getColor(R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setup_viewpager2()


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)

    }

    fun setup_viewpager2() {

        var frg_list = ArrayList<Fragment>()
        frg_list.add(overview())
        frg_list.add(ingredients())
        frg_list.add(instructions())

        var frg_name = ArrayList<String>()
        frg_name.add("overview")
        frg_name.add("ingredient")
        frg_name.add("instruction")


        var bundel = Bundle()
        bundel.putParcelable(Const_var.detail_overview_bundel_key, args.resultRecived)

        bundel.let {
            var adapter_viewpager = detail_adapter(it, frg_list, this)
            bind_detail.detailViewpager2.apply {
                adapter = adapter_viewpager
            }
            TabLayoutMediator(
                bind_detail.detailTablayout,
                bind_detail.detailViewpager2
            ) { tab, pos ->
                tab.text = frg_name[pos]
            }.attach()
        }
    }

}