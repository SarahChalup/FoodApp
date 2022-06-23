package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.PopularItemsBinding
import com.example.foodapp.pojo.CategoryList
import com.example.foodapp.pojo.CategoryMeals
import com.example.foodapp.pojo.MealList

class MostPopulrMealAdapter() : RecyclerView.Adapter<MostPopulrMealAdapter.PopularMealViewHolder>() {

    lateinit var onItemClick: ((CategoryMeals) -> Unit)

    private var mealList = ArrayList<CategoryMeals>()
    fun setMeals(mealsList: ArrayList<CategoryMeals>){
        this.mealList = mealsList
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.bindings.IvPopularMealItem)

        holder.itemView.setOnClickListener{
            onItemClick.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int {
            return mealList.size
    }
    class PopularMealViewHolder(val bindings: PopularItemsBinding) : RecyclerView.ViewHolder(bindings.root)
}