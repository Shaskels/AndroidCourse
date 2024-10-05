package com.example.lab3

import androidx.recyclerview.widget.DiffUtil

class ItemsCallback(private val oldList: List<ListItem>, private val newList: List<ListItem>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return when {
            oldItem is Song && newItem is Song -> {
                oldItem.name == newItem.name
            }
            oldItem is Advertisement && newItem is Advertisement -> {
                oldItem.title == newItem.title
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return when {
            oldItem is Song && newItem is Song -> {
                oldItem.name == newItem.name && oldItem.authorName == newItem.authorName
            }
            oldItem is Advertisement && newItem is Advertisement -> {
                oldItem.theme == newItem.theme && oldItem.title == newItem.title &&
                        oldItem.disc == newItem.disc
            }
            else -> false
        }
    }

}