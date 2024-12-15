package com.example.lab9.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.lab9.domain.Currency

class CurrencyCallback(private val oldList: List<Currency>, private val newList: List<Currency>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.charCode == newItem.charCode
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.name == newItem.name && oldItem.charCode == newItem.charCode
                && oldItem.value == newItem.value && oldItem.nominal == newItem.nominal
    }
}