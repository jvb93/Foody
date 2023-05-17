package com.jvb93.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jvb93.foody.databinding.RecipesRowLayoutBinding
import com.jvb93.foody.models.FoodRecipe
import com.jvb93.foody.util.RecipesDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipes = emptyList<com.jvb93.foody.models.Result>()

    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(result: com.jvb93.foody.models.Result){
                binding.result = result
                binding.executePendingBindings()
            }

            companion object {
                fun from(parent: ViewGroup): MyViewHolder {
                    val layoutInflater  = LayoutInflater.from(parent.context)
                    val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                    return MyViewHolder(binding)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    fun setData(newData: FoodRecipe) {
        val recipesDiffUtil = RecipesDiffUtil(recipes, newData.results)
        val difUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        difUtilResult.dispatchUpdatesTo(this)
    }
}