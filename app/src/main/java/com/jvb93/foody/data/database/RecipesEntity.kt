package com.jvb93.foody.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jvb93.foody.models.FoodRecipe
import com.jvb93.foody.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(var foodRecipe: FoodRecipe) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}