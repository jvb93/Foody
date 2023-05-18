package com.jvb93.foody.ui.fragments.recipes.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.jvb93.foody.databinding.RecipesBottomSheetBinding
import com.jvb93.foody.util.Constants.Companion.DEFAULT_CUISINE
import com.jvb93.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.jvb93.foody.viewmodels.RecipesViewModel
import java.util.Locale

class RecipesBottomSheet : BottomSheetDialogFragment() {

    private var _binding: RecipesBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipesViewModel: RecipesViewModel

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var cuisineChip = DEFAULT_CUISINE
    private var cuisineChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = RecipesBottomSheetBinding.inflate(inflater, container, false)

        recipesViewModel.readMealTypeAndCuisine.asLiveData().observe(viewLifecycleOwner) { value ->
            mealTypeChip = value.selectedMealType
            cuisineChip = value.selectedCuisine
            updateChip(value.selectedMealTypeId, binding.mealTypeChipGroup)
            updateChip(value.selectedCuisineId, binding.cuisineChipGroup)
        }

        binding.mealTypeChipGroup.setOnCheckedStateChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId.first())
            val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
            mealTypeChip = selectedMealType
            mealTypeChipId = selectedChipId.first()
        }

        binding.cuisineChipGroup.setOnCheckedStateChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId.first())
            val selectedCuisine = chip.text.toString().lowercase(Locale.ROOT)
            cuisineChip = selectedCuisine
             cuisineChipId = selectedChipId.first()
        }

        binding.applyButton.setOnClickListener {
            recipesViewModel.saveMealTypeAndCuisine(
                mealTypeChip,
                mealTypeChipId,
                cuisineChip,
                cuisineChipId
            )

            val action = RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun updateChip(selectedId: Int, chipGroup: ChipGroup) {
        if(selectedId != 0)
        {
            try {
                chipGroup.findViewById<Chip>(selectedId).isChecked = true
            }catch (e: Exception ){
                Log.d("RecipesBottomSheet", e.message.toString())
            }
        }
    }
}