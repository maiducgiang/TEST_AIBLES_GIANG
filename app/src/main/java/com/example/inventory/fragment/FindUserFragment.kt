package com.example.inventory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.inventory.InventoryApplication
import com.example.inventory.InventoryViewModel
import com.example.inventory.InventoryViewModelFactory
import com.example.inventory.R
import com.example.inventory.databinding.FragmentFindUserBinding

class FindUserFragment : Fragment() {
    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.userDao()
        )
    }
    private lateinit var binding: FragmentFindUserBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_find_user, container, false)
        val view: View = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.findAction.setOnClickListener {
            if(binding.findUserLogin.text != null){
                getData(binding.findUserLogin.text.toString())
                val action = FindUserFragmentDirections.actionFindUserFragmentToDetailUserFragment(1)
                findNavController().navigate(action)
            }
        }

    }
    private fun getData(login: String ){
    }
}