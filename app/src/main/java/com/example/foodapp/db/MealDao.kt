package com.example.foodapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodapp.pojo.Meal

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeal (meal: Meal)
        //inserta, y si la comida ya existe, la actualiza



    @Delete
    suspend fun delete(meal: Meal)


    @Query("SELECT * FROM mealInformation")
    fun getAllMeals():LiveData<List<Meal>>



}