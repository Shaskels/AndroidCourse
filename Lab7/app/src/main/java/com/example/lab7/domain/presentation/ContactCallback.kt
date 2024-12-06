package com.example.lab7.domain.presentation

import androidx.recyclerview.widget.DiffUtil

class ContactCallback(private val oldList: List<ListItem>, private val newList: List<ListItem>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return when {
            oldItem is ContactFromLocal && newItem is ContactFromLocal -> {
                true
            }

            oldItem is ContactFromDevice && newItem is ContactFromDevice -> {
                true
            }

            else -> false
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return when {
            oldItem is ContactFromLocal && newItem is ContactFromLocal -> {
                oldItem.id == newItem.id && oldItem.name == newItem.name && oldItem.number == newItem.number
            }

            oldItem is ContactFromDevice && newItem is ContactFromDevice -> {
                oldItem.id == newItem.id && oldItem.name == newItem.name && oldItem.number == newItem.number
            }

            else -> false
        }
    }

}