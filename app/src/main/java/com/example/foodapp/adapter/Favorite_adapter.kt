package com.example.foodapp.adapter

import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.data.database.entity.FavoriteEntity
import com.example.foodapp.databinding.ItemFavoriteBinding
import com.example.foodapp.utils.recipes_diff_util
import com.example.foodapp.view.fragment.favorite_recipes_frgDirections
import com.example.foodapp.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar


class Favorite_adapter(var requieractivity: FragmentActivity,var mainViewModel: MainViewModel) :
    RecyclerView.Adapter<Favorite_adapter.viewholder>(), ActionMode.Callback {
    var favorite_list = emptyList<FavoriteEntity>()
    lateinit var actionMode: ActionMode
    var multiselection = false
    lateinit var rootview:View
    var selected_favorite = arrayListOf<FavoriteEntity>()
    var selected_favorite_viewholder = arrayListOf<viewholder>()


    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favorite_contexual_menu, menu)
        actionMode=mode!!
        applychangecolor_lonclick_statusbar(R.color.gray)
        return true
    }






    class viewholder(var favorite_bind: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(favorite_bind.root) {
        fun bind(favoriteEntity: com.example.foodapp.data.database.entity.FavoriteEntity) {
            favorite_bind.favorite = favoriteEntity
            favorite_bind.executePendingBindings()
        }



        companion object {
            fun from(viewGroup: ViewGroup): viewholder {
                var bind = ItemFavoriteBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return viewholder(bind)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        return viewholder.from(parent)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.bind(favorite_list[position])
        rootview=holder.itemView.rootView
        selected_favorite_viewholder.add(holder)
        /**single click-----------------------------*/
        holder.favorite_bind.favoriteItemCardview.setOnClickListener {

            if (multiselection) {

                applyselected_favorite_to_arraylist(favorite_list[position], holder)
            } else if (multiselection == false) {
                Log.e("one", "two")
                var action =
                    favorite_recipes_frgDirections.actionFavoriteRecipesFrgToDetailActivity(
                        favorite_list[position].result
                    )
                holder.itemView.findNavController().navigate(action)
            }


        }
        /**on long click-----------------------------*/
        holder.favorite_bind.favoriteItemCardview.setOnLongClickListener {
            if (!multiselection) {
                requieractivity.startActionMode(this)
                multiselection = true
                applyselected_favorite_to_arraylist(favorite_list[position], holder)

                true
            } else {
                multiselection = false
                false
            }

        }


    }


    override fun getItemCount(): Int = favorite_list.size


    fun applyselected_favorite_to_arraylist(current: FavoriteEntity, holder: viewholder) {
        if (selected_favorite.contains(current)) {
            selected_favorite.remove(current)
            change_selected_recipes(holder, R.color.white, R.color.white, 1)
            destroy_actionmode_when_no_data()
        } else {
            change_selected_recipes(holder, R.color.purple_200, R.color.purple_500, 1)
            selected_favorite.add(current)
            destroy_actionmode_when_no_data()
        }
    }

    fun change_selected_recipes(
        viewholder: viewholder,
        backgroundcolor: Int,
        strockcolor: Int,
        strockwidth: Int
    ) {
        viewholder.favorite_bind.favoriteItemCardview.setBackgroundColor(
            ContextCompat.getColor(
                requieractivity,
                strockcolor
            )
        )
        viewholder.favorite_bind.favoriteItemCardview.strokeWidth = strockwidth
        viewholder.favorite_bind.favoriteItemCardview.setStrokeColor(
            ContextCompat.getColor(
                requieractivity,
                backgroundcolor
            )
        )

    }

    fun setdata(newfavoritelist: List<FavoriteEntity>) {
        var diff = recipes_diff_util(newfavoritelist, favorite_list)
        var difresualt = DiffUtil.calculateDiff(diff)
        favorite_list = newfavoritelist
        difresualt.dispatchUpdatesTo(this)
    }



    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {

        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if(item?.itemId==R.id.favorite_delete_menu)  {
            selected_favorite.forEach {
                mainViewModel.delete_favorite_toDataBase_local(it)

            }
            showsnack("${selected_favorite.size} was deleted")
            selected_favorite.clear()
            multiselection=false
            actionMode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        multiselection = false
        selected_favorite_viewholder.forEach {
            change_selected_recipes(it,R.color.white,R.color.white,1)
        }
        selected_favorite.clear()
        applychangecolor_lonclick_statusbar(R.color.secondaryColor)
    }
    fun destroy_actionmode_when_no_data(){
        when(selected_favorite.size){
            0 -> {
                actionMode?.finish()
            }
            1->{
                actionMode?.title = "${selected_favorite.size} Item Selected"
            }
            else->{
                actionMode?.title = "${selected_favorite.size} Items Selected"
            }
        }
    }

    fun applychangecolor_lonclick_statusbar(color: Int) {
        requieractivity.window.statusBarColor = ContextCompat.getColor(requieractivity, color)
    }

    fun showsnack(message:String){
        Snackbar.make(rootview,message,Snackbar.LENGTH_SHORT).show()
    }
    fun destroy_actionmode_when_goanother_fragmnet(){
        if(this::actionMode.isInitialized){
            actionMode.finish()
        }
    }
}