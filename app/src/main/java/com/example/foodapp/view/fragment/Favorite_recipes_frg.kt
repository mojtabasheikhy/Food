package com.example.foodapp.view.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.adapter.Favorite_adapter
import com.example.foodapp.databinding.FragmentFavoriteRecipesFrgBinding
import com.example.foodapp.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class favorite_recipes_frg : Fragment() {
    val mainviewmodel_provider: MainViewModel by viewModels()
    val favoriteAdapte: Favorite_adapter by lazy {
        Favorite_adapter(
            requireActivity(),
            mainviewmodel_provider
        )
    }

    var bindings: FragmentFavoriteRecipesFrgBinding? = null
    val _binding get() = bindings!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindings = FragmentFavoriteRecipesFrgBinding.inflate(inflater, container, false)
        bindings?.lifecycleOwner = this
        bindings?.mainviewmodel = mainviewmodel_provider
        bindings?.favoriteadpter = favoriteAdapte
        setHasOptionsMenu(true)
        favorite_recycler_setup(bindings!!.favoriteRecycler)

        return bindings?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_all_favorite_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.delete_all){
             mainviewmodel_provider.deleteall_favorite_toDataBase_local()
             Snackbar.make(bindings!!.root,"all favorite was deleted",Snackbar.LENGTH_SHORT).show()
            return true

        }
        else return false
    }

    fun favorite_recycler_setup(recyclerView: RecyclerView) {
        recyclerView.adapter = favoriteAdapte
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        bindings = null
        favoriteAdapte.destroy_actionmode_when_goanother_fragmnet()
    }
}