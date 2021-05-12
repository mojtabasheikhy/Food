package com.example.foodapp.view.fragment.overview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.example.foodapp.R
import com.example.foodapp.data.database.entity.FavoriteEntity
import com.example.foodapp.model.Result
import com.example.foodapp.utils.Const_var
import com.example.foodapp.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_overview.view.*
import org.jsoup.Jsoup

@AndroidEntryPoint
class overview : Fragment() {

    var saved_favorite = false
    var saved_favorite_id = 0

    val viewmodel_favorite: MainViewModel by viewModels()

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_overview, container, false)
        setHasOptionsMenu(true)

        var bundle = arguments?.getParcelable<Result>(Const_var.detail_overview_bundel_key)
        view.overview_main_iv.load(bundle?.image)

        view.overview_tv_title.text = bundle?.title.toString()
        view.overview_tv_like.text = bundle?.aggregateLikes.toString()
        view.overview_tv_time.text = bundle?.readyInMinutes.toString()
        bundle?.summary.let {
            var jsoup = Jsoup.parse(it).text()
            view.overview_tv_desc.text = jsoup
        }
        view.overview_tv_title.text = bundle?.title
        view.overview_tv_title.text = bundle?.title

        if (bundle?.vegetarian == true) {
            view.overview_tv_vegetarian.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            view.overView_mark_vegetarian.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (bundle?.vegan == true) {
            view.overview_tv_vegan.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            view.overview_mark_vegan.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (bundle?.glutenFree == true) {
            view.overview_tv_glutenfree.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            view.overview_mark_gluten.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (bundle?.veryHealthy == true) {
            view.overview_tv_Healthy.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            view.overview_mark_healthy.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (bundle?.dairyFree == true) {
            view.overview_tv_dairyfree.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            view.overview_mark_dairyfree.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (bundle?.cheap == true) {
            view.overview_tv_cheap.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            view.overview_mark_cheap.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
        var menuitem = menu.findItem(R.id.favorite)
        checksaved_item_tochangecolor(menuitem)


    }

    private fun checksaved_item_tochangecolor(menuitem: MenuItem?) {
        viewmodel_favorite.mld_read_favorite.observe(this, {entity_favorite->
            try {
                for (saved in entity_favorite) {
                    if (saved.result.id == arguments?.getParcelable<Result>(Const_var.detail_overview_bundel_key)?.id) {
                        menuitem?.let { it1 -> changecolor_star(it1, R.color.yellow) }
                        saved_favorite_id=saved.id
                        saved_favorite=true
                    } else {
                        menuitem?.icon?.setTint(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )

                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite&&saved_favorite==false) {
            save_favorite(item)
            return true
        }
        else if(item.itemId==R.id.favorite&&saved_favorite==true){
            remove_favorite(item)
            return true
        }
        return super.onOptionsItemSelected(item)

    }

    private fun save_favorite(item: MenuItem) {
        var entity = arguments?.getParcelable<Result>(Const_var.detail_overview_bundel_key)?.let {
            FavoriteEntity(
                id = 0,
                it
            )
        }
        if (entity != null) {
            viewmodel_favorite.insert_favorite_toDataBase_local(entity)
        }
        changecolor_star(item, R.color.yellow)
        showsnackbar("item saved")
        saved_favorite = true


    }

    fun remove_favorite(item: MenuItem) {
        var entity: FavoriteEntity =
            arguments?.getParcelable<Result>(Const_var.detail_overview_bundel_key).let {
                FavoriteEntity(saved_favorite_id, result = it!!)
            }
        viewmodel_favorite.delete_favorite_toDataBase_local(entity)
        changecolor_star(item, R.color.white)
        showsnackbar("item deleted")
        saved_favorite =false
    }





private fun showsnackbar(message: String) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.primaryDarkColor))
        .setAction("ok", {})
        .show()
}

private fun changecolor_star(item: MenuItem, yellow: Int) {
    item.icon.setTint(ContextCompat.getColor(requireContext(), yellow))
}


}