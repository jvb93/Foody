package com.jvb93.foody.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.jvb93.foody.data.database.RecipesEntity
import com.jvb93.foody.models.FoodRecipe
import com.jvb93.foody.util.NetworkResult

class RecipesBinding {

    companion object {

        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun errorViewVisibility(
            view: View,
            apiResponse: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ) {

            if(view is ImageView)
            {
                if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                    view.visibility = View.VISIBLE
                } else if (apiResponse is NetworkResult.Loading) {
                    view.visibility = View.INVISIBLE
                } else if (apiResponse is NetworkResult.Success) {
                    view.visibility = View.INVISIBLE

                }
            }

            else if(view is TextView)
            {
                if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                    view.visibility = View.VISIBLE
                    view.text = apiResponse.message.toString()
                } else if (apiResponse is NetworkResult.Loading) {
                    view.visibility = View.INVISIBLE
                    view.text = ""
                } else if (apiResponse is NetworkResult.Success) {
                    view.visibility = View.INVISIBLE
                    view.text = ""
                }
            }


        }
    }

}