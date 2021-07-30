package com.example.inventory.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.*
import com.example.inventory.data.User
import com.example.inventory.databinding.FragmentUserListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

//var code: Int = 0
class UserListFragment : Fragment() {
    lateinit var user: User

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.userDao()
        )
    }

    private lateinit var binding: FragmentUserListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false)
        val view: View = binding.root
        return view
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




    private fun onItemClicked() = { user1: User ->
        val action = UserListFragmentDirections.actionUserListFragmentToDetailUserFragment(user1.id)
        this.findNavController().navigate(action)
    }


    private fun actionIntent() = { url: String ->
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ItemListAdapter(onItemClicked(), actionIntent())

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = adapter
        viewModel.allUsers.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }

        val swipeGesture = object : SwipeGesture(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction){
                    ItemTouchHelper.LEFT ->{
                        val code = viewHolder.adapterPosition
                        user = adapter.putUser(code)
                        showConfirmationDialog()
                    }
                }

            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(recyclerView)

        binding.buttonAdd.setOnClickListener {
            val action = UserListFragmentDirections.actionUserListFragmentToAddUserFragment(
                getString(R.string.add_fragment_title)
            )
            this.findNavController().navigate(action)
        }
        binding.buttonFind.setOnClickListener {
            val action = UserListFragmentDirections.actionUserListFragmentToFindUserFragment()
            this.findNavController().navigate(action)
        }
    }
}