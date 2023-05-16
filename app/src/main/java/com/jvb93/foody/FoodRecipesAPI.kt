package com.jvb93.foody

import com.jvb93.foody.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipesAPI {
    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap query: Map<String, String>
    ): Response<FoodRecipe>
}