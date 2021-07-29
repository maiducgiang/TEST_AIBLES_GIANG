
package com.example.inventory.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.data.User
import com.example.inventory.databinding.ItemListItemBinding


class ItemListAdapter(private val onItemClicked: (User) -> Unit) :
    ListAdapter<User, ItemListAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class ItemViewHolder(private var binding: ItemListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.itemId.text = user.id.toString()
            binding.itemLogin.text = user.userLogin
            binding.itemType.text = user.userType
            binding.itemUrl.text = user.userUrl
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldUser: User, newUser: User): Boolean {
                return oldUser === newUser
            }

            override fun areContentsTheSame(oldUser: User, newUser: User): Boolean {
                return oldUser.userLogin == newUser.userLogin
            }
        }
    }
}
