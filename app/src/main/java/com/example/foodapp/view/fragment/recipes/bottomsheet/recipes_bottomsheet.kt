package com.example.foodapp.view.fragment.recipes.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.foodapp.R
import com.example.foodapp.utils.Const_var
import com.example.foodapp.viewmodel.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.recipes_bottomsheet.view.*
import java.util.*


class recipes_bottomsheet : BottomSheetDialogFragment() {

    var chip_meal_selected = Const_var.default_mealType
    var chip_meal_selected_id = 0
    var chip_diet_selected = Const_var.default_dietType
    var chip_diet_selected_id = 0

    lateinit var recipesviewmodel_provider: RecipesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesviewmodel_provider = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.recipes_bottomsheet, container, false)
        recipesviewmodel_provider.read_Data_bs_selected.asLiveData().observe(viewLifecycleOwner, {
            chip_meal_selected = it.selectedMeal_type
            chip_diet_selected = it.selectedDiet_type
            load_chip_selected_item_meal(it.selectedMeal_type_id, view.recipes_bs_meal_chipgroup)
            load_chip_selected_item_meal(it.selectedDiet_type_id, view.recipes_bs_diet_chipgroup)
        })
        view.recipes_bs_meal_chipgroup.setOnCheckedChangeListener { group, checkedId ->

            var Checked_Selected_Id_Meal = group.findViewById<Chip>(checkedId)
            var checked_selected_Id_Meal_string = Checked_Selected_Id_Meal.text.toString().toLowerCase(Locale.ROOT)
            chip_meal_selected = checked_selected_Id_Meal_string
            chip_meal_selected_id = checkedId

        }
        view.recipes_bs_diet_chipgroup.setOnCheckedChangeListener { group, checkedId ->
            var Checked_Selected_Id_diet = group.findViewById<Chip>(checkedId)
            var checked_selected_Id_diet_string =
                Checked_Selected_Id_diet.text.toString().toLowerCase(Locale.ROOT)
            chip_diet_selected = checked_selected_Id_diet_string
            chip_diet_selected_id = checkedId

        }
        view.recipes_bs_apply_btn.setOnClickListener {

            recipesviewmodel_provider.save_data_bs_selected(
                chip_meal_selected,
                chip_meal_selected_id,
                chip_diet_selected,
                chip_diet_selected_id
            )
            var action=recipes_bottomsheetDirections.actionRecipesBottomsheetToRecipesFrg(true)
            findNavController().navigate(action)
        }
        return view
    }

    private fun load_chip_selected_item_meal(
        selecteddietTypeId: Int,
        recipesBsMealChipgroup: ChipGroup?
    ) {
        try {

            if (selecteddietTypeId != 0) {
                recipesBsMealChipgroup?.findViewById<Chip>(selecteddietTypeId)?.isChecked = true
            }
            else{
                Log.e("t2", "Error")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}