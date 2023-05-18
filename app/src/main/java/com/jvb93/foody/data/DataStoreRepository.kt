package com.jvb93.foody.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jvb93.foody.util.Constants.Companion.DEFAULT_CUISINE
import com.jvb93.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.jvb93.foody.util.Constants.Companion.PREFERENCES_CUISINE
import com.jvb93.foody.util.Constants.Companion.PREFERENCES_CUISINE_ID
import com.jvb93.foody.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.jvb93.foody.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.jvb93.foody.util.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedCuisine = stringPreferencesKey(PREFERENCES_CUISINE)
        val selectedCuisineId = intPreferencesKey(PREFERENCES_CUISINE_ID)

    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveMealTypeAndCuisine(mealType: String, mealTypeId: Int, cuisine: String, cuisineId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKeys.selectedCuisine] = cuisine
            preferences[PreferenceKeys.selectedCuisineId] = cuisineId
        }
    }

    val readMealTypeAndCuisine: Flow<MealTypeAndCuisine> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedMealType = preferences[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
            val selectedCuisine = preferences[PreferenceKeys.selectedCuisine] ?: DEFAULT_CUISINE
            val selectedCuisineId = preferences[PreferenceKeys.selectedCuisineId] ?: 0
            MealTypeAndCuisine(
                selectedMealType,
                selectedMealTypeId,
                selectedCuisine,
                selectedCuisineId
            )
        }
}

data class MealTypeAndCuisine (
    val selectedMealType : String,
    val selectedMealTypeId : Int,
    val selectedCuisine : String,
    val selectedCuisineId : Int
)