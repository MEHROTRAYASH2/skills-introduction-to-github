package com.foodorder.app.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.foodorder.app.FoodOrderApplication
import com.foodorder.app.databinding.FragmentCartBinding
import com.foodorder.app.ui.checkout.CheckoutActivity
import com.foodorder.app.ui.main.adapters.CartAdapter

class CartFragment : Fragment() {
    
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val application = requireActivity().application as FoodOrderApplication
        val factory = CartViewModelFactory(application.cartRepository)
        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]
        
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }
    
    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            onQuantityChange = { cartItem, newQuantity ->
                cartViewModel.updateQuantity(cartItem.id, newQuantity)
            },
            onRemoveItem = { cartItem ->
                cartViewModel.removeFromCart(cartItem)
            }
        )
        
        binding.recyclerViewCart.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }
    
    private fun setupClickListeners() {
        binding.btnCheckout.setOnClickListener {
            val intent = Intent(requireContext(), CheckoutActivity::class.java)
            startActivity(intent)
        }
        
        binding.btnClearCart.setOnClickListener {
            cartViewModel.clearCart()
        }
    }
    
    private fun observeViewModel() {
        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            cartAdapter.submitList(cartItems)
            updateEmptyState(cartItems.isEmpty())
        }
        
        cartViewModel.cartTotal.observe(viewLifecycleOwner) { total ->
            binding.tvTotal.text = "Total: ₹${String.format("%.2f", total ?: 0.0)}"
            binding.btnCheckout.isEnabled = (total ?: 0.0) > 0
        }
        
        cartViewModel.itemCount.observe(viewLifecycleOwner) { count ->
            binding.tvItemCount.text = "$count items"
        }
    }
    
    private fun updateEmptyState(isEmpty: Boolean) {
        if (isEmpty) {
            binding.emptyCartLayout.visibility = View.VISIBLE
            binding.cartContentLayout.visibility = View.GONE
        } else {
            binding.emptyCartLayout.visibility = View.GONE
            binding.cartContentLayout.visibility = View.VISIBLE
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}