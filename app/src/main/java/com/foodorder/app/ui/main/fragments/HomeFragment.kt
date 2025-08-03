package com.foodorder.app.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.foodorder.app.FoodOrderApplication
import com.foodorder.app.databinding.FragmentHomeBinding
import com.foodorder.app.ui.main.adapters.RestaurantAdapter
import com.foodorder.app.ui.restaurant.RestaurantDetailActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var restaurantAdapter: RestaurantAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val application = requireActivity().application as FoodOrderApplication
        val factory = HomeViewModelFactory(application.restaurantRepository)
        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        
        setupRecyclerView()
        setupSearchView()
        observeViewModel()
        loadRestaurants()
    }
    
    private fun setupRecyclerView() {
        restaurantAdapter = RestaurantAdapter { restaurant ->
            val intent = Intent(requireContext(), RestaurantDetailActivity::class.java)
            intent.putExtra("restaurant", restaurant)
            startActivity(intent)
        }
        
        binding.recyclerViewRestaurants.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = restaurantAdapter
        }
    }
    
    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchRestaurants(it) }
                return true
            }
            
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    loadRestaurants()
                }
                return true
            }
        })
    }
    
    private fun observeViewModel() {
        homeViewModel.restaurants.observe(viewLifecycleOwner) { restaurants ->
            restaurantAdapter.submitList(restaurants)
        }
        
        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        
        homeViewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun loadRestaurants() {
        lifecycleScope.launch {
            homeViewModel.loadRestaurants()
        }
    }
    
    private fun searchRestaurants(query: String) {
        lifecycleScope.launch {
            homeViewModel.searchRestaurants(query)
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}