package com.example.foodapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.activities.MainActivity
import com.example.foodapp.adapters.FavoriteMealAdapter
import com.example.foodapp.databinding.FragmentFavoritesBinding
import com.example.foodapp.viewModel.HomeViewModel


class FavoritesFragment : Fragment() {
    private lateinit var binding : FragmentFavoritesBinding
    private lateinit var viewmModel: HomeViewModel
    private lateinit var favoriteAdapter : FavoriteMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavoritesLiveData()
    }

    private fun prepareRecyclerView() {
        favoriteAdapter = FavoriteMealAdapter()
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL, false)
            adapter = favoriteAdapter
        }
    }

    private fun observeFavoritesLiveData() {
        viewmModel.observeFavoritesMealLiveData().observe(viewLifecycleOwner, Observer {
            meals ->
           favoriteAdapter.differ.submitList(meals)
        })
    }
}