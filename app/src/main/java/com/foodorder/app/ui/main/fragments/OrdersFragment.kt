package com.foodorder.app.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.foodorder.app.databinding.FragmentOrdersBinding

class OrdersFragment : Fragment() {
    
    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // TODO: Implement orders functionality
        binding.tvOrdersPlaceholder.text = "Your order history will appear here"
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}