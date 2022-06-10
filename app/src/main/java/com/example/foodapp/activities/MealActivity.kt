package com.example.foodapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.fragments.HomeFragment
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMealBinding
    private lateinit var mealId: String
    private lateinit var mealName:String
    private lateinit var mealThumb: String
    //viemodel
    private lateinit var mealMvvm: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm = ViewModelProvider(this )[MealViewModel::class.java]
        getMealInfoFromIntent()
        setInformationInViews()
        mealMvvm.getMealDetail(mealId)
        observeMealDetailsLiveData()
    }

    private fun observeMealDetailsLiveData() {
        mealMvvm.observeMealsDetailsLiveData().observe(this , object : Observer<Meal>{


            override fun onChanged(t: Meal?) {
                val meal = t
                binding.tvMealcategory.text = "Category : ${meal!!.strCategory}"
                binding.tvMealarea.text = "Area : ${meal!!.strArea}"
                binding.tvInstrucion.text = meal!!.strInstructions
            }
        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgCollapToolbar)

        binding.colapToolbar.title = mealName
        binding.colapToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
    }

    private fun getMealInfoFromIntent() {
        val intent = intent
        mealId=intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }


}