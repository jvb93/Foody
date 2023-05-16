package com.jvb93.foody

import com.jvb93.foody.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

abstract class RemoteDataSource @Inject constructor(
    private val foodRecipesAPI: FoodRecipesAPI
) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesAPI.getRecipes(queries)
    }
}