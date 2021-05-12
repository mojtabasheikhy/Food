package com.example.foodapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*

import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.R
import com.example.foodapp.adapter.recipes_adapter
import com.example.foodapp.databinding.FragmentRecipesFrgBinding
import com.example.foodapp.utils.NetworkListener
import com.example.foodapp.utils.NetworkResualt
import com.example.foodapp.utils.observeOnce
import com.example.foodapp.viewmodel.MainViewModel
import com.example.foodapp.viewmodel.RecipesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class recipes_frg : Fragment(), View.OnClickListener, SearchView.OnQueryTextListener {

    val args by navArgs<recipes_frgArgs>()
    lateinit var networkListener: NetworkListener

    var recipes_binding: FragmentRecipesFrgBinding? = null
    val recipes_binding_getter get() = recipes_binding

    lateinit var mainViewModel: MainViewModel
    lateinit var recipesViewModel: RecipesViewModel
    private val mdadpter by lazy { recipes_adapter(requireContext()) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(
            requireActivity(),
            defaultViewModelProviderFactory
        ).get(com.example.foodapp.viewmodel.MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        recipes_binding = FragmentRecipesFrgBinding.inflate(inflater, container, false)
        recipes_binding_getter?.lifecycleOwner = this
        recipes_binding_getter?.recipesmain = mainViewModel
        setuprecyclerview()

        recipesViewModel.readbackonline.observe(viewLifecycleOwner, {
            recipesViewModel.backOnline = it
        })
        setHasOptionsMenu(true)

        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailable(requireContext()).collect { status ->
                Log.e("online", status.toString())
                recipesViewModel.status_network = status
                recipesViewModel.toast_state_status(recipes_binding!!.recipesShimerrecycler)
                Read_database_local()
            }
        }

        // requestapi()
        recipes_binding!!.recipesFab.setOnClickListener(this)

        return recipes_binding!!.root
    }

    fun setuprecyclerview() {
        recipes_binding?.recipesShimerrecycler?.layoutManager = LinearLayoutManager(requireContext())

        recipes_binding?.recipesShimerrecycler?.adapter = mdadpter
        showshimer()

    }

    fun Read_database_local() {

        lifecycleScope.launch {
            mainViewModel.mld_read_localdatabase.observeOnce(viewLifecycleOwner, {
                if (it.isNotEmpty() && !args.backfrombottomshit) {
                    Log.e("frg", "read database called!!")
                    mdadpter.setdata(it[0].foodrecipes)
                    hideshimer()
                } else {
                    showshimer()
                    requestapi()
                }
            })
        }
    }

    fun loaddatabase_when_networkerorr() {
        lifecycleScope.launch {
            mainViewModel.mld_read_localdatabase.observe(viewLifecycleOwner, {
                if (it.isNotEmpty()) {
                    mdadpter.setdata(it[0].foodrecipes)
                }
            })
        }
    }

    fun requestapi() {

        Log.e("frg", "request api called!!")
        mainViewModel.getrecipes_online(recipesViewModel.applyhashmap())
        mainViewModel.mld_getdata_recipes_response.observe(viewLifecycleOwner, {
            when (it) {
                is NetworkResualt.success -> {
                    hideshimer()
                    it.data?.let {
                        mdadpter.setdata(it)
                    }
                }
                is NetworkResualt.Error -> {
                    loaddatabase_when_networkerorr()
                    hideshimer()
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResualt.loading -> {
                    showshimer()
                }
            }

        })
    }

    fun recipes_search(searchqury: String) {
        showshimer()
        mainViewModel.search_recipes_query(recipesViewModel.apply_search_hashmap(searchqury))
        mainViewModel.mld_searched_recipes_response.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResualt.success -> {
                    hideshimer()
                    response.data?.let { mdadpter.setdata(it) }
                }
                is NetworkResualt.Error -> {
                    hideshimer()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    loaddatabase_when_networkerorr()
                }
                is NetworkResualt.loading
                -> {
                    showshimer()
                }
            }
        })
    }


    fun showshimer() {
        recipes_binding?.recipesShimerrecycler?.showShimmer()
    }

    fun hideshimer() {
        recipes_binding?.recipesShimerrecycler?.hideShimmer()
    }


    override fun onDestroy() {
        super.onDestroy()
        recipes_binding = null
    }

    @SuppressLint("ResourceAsColor")
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.recipes_fab -> {
                if (recipesViewModel.status_network) {
                    findNavController().navigate(R.id.action_recipes_frg_to_recipes_bottomsheet)
                } else {
                    Snackbar.make(
                        recipes_binding!!.recipesFab,
                        "No Network connection",
                        Snackbar.LENGTH_SHORT
                    )
                        .setBackgroundTint(R.color.primaryColor)
                        .show()
                }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu, menu)
        var search = menu.findItem(R.id.recipes_menu_search)
        var action = search.actionView as? SearchView
        action?.isSubmitButtonEnabled = true
        action?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!=null){
        recipes_search(query)}
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }


}