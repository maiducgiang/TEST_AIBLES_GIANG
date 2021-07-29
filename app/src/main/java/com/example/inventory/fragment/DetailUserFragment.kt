package com.example.inventory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventory.InventoryApplication
import com.example.inventory.InventoryViewModel
import com.example.inventory.InventoryViewModelFactory
import com.example.inventory.R
import com.example.inventory.data.User
import com.example.inventory.databinding.FragmentDetailUserBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class DetailUserFragment : Fragment() {
    private val navigationArgs: DetailUserFragmentArgs by navArgs()
    lateinit var user: User

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.userDao()
        )
    }

    private var _binding: FragmentDetailUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun bind(user: User) {
        binding.apply {
            userLogin.text = user.userLogin
            userType.text = user.userType
            userUrl.text = user.userUrl
            sellUser.isEnabled = viewModel.isStockAvailable(user)
            sellUser.setOnClickListener { viewModel.sellUser(user) }
            deleteUser.setOnClickListener { showConfirmationDialog() }
            editUser.setOnClickListener { editUser() }
        }
    }
    private fun editUser() {

    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteUser()
            }
            .show()
    }

    private fun deleteUser() {
        viewModel.deleteUser(user)
        findNavController().navigateUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.userId
        viewModel.retrieveUser(id).observe(this.viewLifecycleOwner) { selectedUser ->
            user = selectedUser
            bind(user)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}