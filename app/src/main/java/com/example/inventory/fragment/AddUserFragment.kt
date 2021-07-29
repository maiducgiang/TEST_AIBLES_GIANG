package com.example.inventory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventory.InventoryApplication
import com.example.inventory.InventoryViewModel
import com.example.inventory.InventoryViewModelFactory
import com.example.inventory.R
import com.example.inventory.data.User
import com.example.inventory.databinding.FragmentAddUserBinding

class AddUserFragment : Fragment() {

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database
                .userDao()
        )
    }
    private val navigationArgs: AddUserFragmentArgs by navArgs()

    lateinit var user: User

    private lateinit var binding: FragmentAddUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_user, container, false)
        return binding.root
    }
    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.userLogin.text.toString(),
            binding.userType.text.toString(),
            binding.userUrl.text.toString(),
        )
    }

    private fun addNewUser() {
        if (isEntryValid()) {
            viewModel.addNewUser(
                binding.userLogin.text.toString(),
                binding.userType.text.toString(),
                binding.userUrl.text.toString(),
            )
            val action = AddUserFragmentDirections.actionAddUserFragmentToUserListFragment()
            findNavController().navigate(action)
        }
    }

    private fun updateUser() {
        if (isEntryValid()) {
            viewModel.updateUser(
                this.navigationArgs.userId,
                this.binding.userLogin.text.toString(),
                this.binding.userType.text.toString(),
                this.binding.userUrl.text.toString()
            )
            val action = AddUserFragmentDirections.actionAddUserFragmentToDetailUserFragment(this.navigationArgs.userId)
            this.findNavController().navigate(action)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveAction.setOnClickListener {
            addNewUser()
        }
    }
}
