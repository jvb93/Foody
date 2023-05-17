package com.jvb93.foody.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.jvb93.foody.util.Constants
import com.jvb93.foody.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.jvb93.foody.util.Constants.Companion.QUERY_API_KEY
import com.jvb93.foody.util.Constants.Companion.QUERY_CUISINE
import com.jvb93.foody.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.jvb93.foody.util.Constants.Companion.QUERY_NUMBER

class RecipesViewModel(application: Application) : AndroidViewModel(application) {
     fun applyQueries() : HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_NUMBER] = "50"
        queries[QUERY_CUISINE] = "Mediterranean"
        queries[QUERY_API_KEY] = Constants.API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }
}