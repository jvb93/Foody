package com.jvb93.foody.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jvb93.foody.data.DataStoreRepository
import com.jvb93.foody.util.Constants.Companion.DEFAULT_CUISINE
import com.jvb93.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.jvb93.foody.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.jvb93.foody.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.jvb93.foody.util.Constants.Companion.QUERY_CUISINE
import com.jvb93.foody.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.jvb93.foody.util.Constants.Companion.QUERY_NUMBER
import com.jvb93.foody.util.Constants.Companion.QUERY_SEARCH
import com.jvb93.foody.util.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(application: Application, private val dataStoreRepository: DataStoreRepository) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var cuisine = DEFAULT_CUISINE

    var networkStatus = false

    val readMealTypeAndCuisine = dataStoreRepository.readMealTypeAndCuisine

    fun saveMealTypeAndCuisine(mealType: String, mealTypeId: Int, cuisine: String, cuisineId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealTypeAndCuisine(mealType, mealTypeId, cuisine, cuisineId)
        }

    fun applyQueries() : HashMap<String, String>{

        viewModelScope.launch {
            dataStoreRepository.readMealTypeAndCuisine.collect{ value ->
                mealType = value.selectedMealType
                cuisine = value.selectedCuisine
            }
        }

        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_CUISINE] = cuisine
        queries[QUERY_TYPE] = mealType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        queries["sort"] = "meta-score"
        queries["sortDirection"] = "desc"

        return queries
    }

    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        queries["sort"] = "meta-score"
        queries["sortDirection"] = "desc"

        return queries
    }

    fun showNetworkStatus(){
        if(!networkStatus){
            Toast.makeText(getApplication(), "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }
}