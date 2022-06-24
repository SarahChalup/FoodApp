package com.example.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.activities.CategoryMealsActivity
import com.example.foodapp.activities.MealActivity
import com.example.foodapp.adapters.CategoriesAdapter
import com.example.foodapp.adapters.MostPopulrMealAdapter
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.MealsByCategory
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.HomeViewModel

class HomeFragment : Fragment() {

private lateinit var binding:FragmentHomeBinding
private lateinit var homeMvvm:HomeViewModel
private lateinit var randomMeal:Meal
private lateinit var popularItemAdapter: MostPopulrMealAdapter
private lateinit var categoriesAdapter: CategoriesAdapter


companion object{
    const val MEAL_ID = "com.example.foodapp.fragments.idMeal"
    const val MEAL_NAME = "com.example.foodapp.fragments.nameMeal"
    const val MEAL_THUMB = "com.example.foodapp.fragments.thumbMeal"
    const val CATEGORY_NAME = "com.example.foodapp.fragments.CategoryName"
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]

        popularItemAdapter = MostPopulrMealAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemRecyclerView()

        homeMvvm.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()

        homeMvvm.getPopularItems()
        observePpularItemLiveData()
        onPopularItemClick()

        prepareCategoriesRecylcerView()
        homeMvvm.getCategories()
        observeCategoryLiveData()
        oncCategoryClick()


    }

    private fun oncCategoryClick() {
        categoriesAdapter.onItemClick = {
            category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecylcerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(
                context,
            3,
                GridLayoutManager.VERTICAL,
                false
            )
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoryLiveData() {
        homeMvvm.observeCategoriesLiveData().observe(viewLifecycleOwner) { categories ->
               categoriesAdapter.setCategoriesList(categories as ArrayList<Category>)

        }
    }

    private fun onPopularItemClick() {
        popularItemAdapter.onItemClick = {
            meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemRecyclerView() {
        binding.rvPopularItem.apply {

            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemAdapter
        }
    }

    private fun observePpularItemLiveData() {
        homeMvvm.observePopularItemLiveData().observe(viewLifecycleOwner)
        { mealList ->
            popularItemAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory> /* = java.util.ArrayList<com.example.foodapp.pojo.CategoryMeals> */)
        }
    }

    private fun onRandomMealClick() {
        binding.cvCarview.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        homeMvvm.observeRandomMealLiveData()
            .observe(viewLifecycleOwner
            ) { t ->
                Glide.with(this@HomeFragment)
                    .load(t!!.strMealThumb)
                    .into(binding.imgRandomMeal)
                this.randomMeal = t
            }

    }


}