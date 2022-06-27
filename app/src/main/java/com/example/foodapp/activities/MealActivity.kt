package com.example.foodapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.db.MealDatabase
import com.example.foodapp.fragments.HomeFragment
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.MealViewModel
import com.example.foodapp.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var mealMvvm: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase =  MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]
     //   mealMvvm = ViewModelProvider(this)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setInformationIView()
        mealMvvm.getMealDetail(mealId)
        obseveMealDetailsLifeData()

        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.btnAddToFav.setOnClickListener {
            mealToSave?.let { it -> mealMvvm.insertMeal(it) }
            Toast.makeText(this, "Meal saved", Toast.LENGTH_SHORT).show()
        }
    }

    private var mealToSave : Meal? = null
    private fun obseveMealDetailsLifeData() {
        mealMvvm.observeMealsDetailsLiveData().observe(this, object  : Observer<Meal>{
            override fun onChanged(t: Meal?) {
                val meal = t
                mealToSave = meal

                binding.tvCategories.text = ("Categoría: ${meal!!.strCategory}")
                binding.tvArea.text = ("Area: ${meal!!.strArea}")
                binding.tvInstructions.text = meal!!.strInstructions
            }
        })
    }

    private fun setInformationIView() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }
}