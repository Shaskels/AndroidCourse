package com.example.lab7.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lab7.R
import com.example.lab7.ui.ContactFromLocalFragment.Companion.ID_KEY
import com.example.lab7.ui.ContactFromLocalFragment.Companion.NAME_KEY
import com.example.lab7.ui.ContactFromLocalFragment.Companion.NUMBER_KEY
import com.example.lab7.ui.ContactsListFragment.Companion.CONTACT_LIST_FRAGMENT_IN_BACK_STACK
import com.example.lab7.ui.ContactFromLocalFragment
import com.example.lab7.ui.ContactFromDeviceFragment


class ContactAdapter(private val fragmentManager: FragmentManager?) :
    RecyclerView.Adapter<ContactAdapter.BaseViewHolder>() {

    private val items = ArrayList<ListItem>()

    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    class ContactsFromDBViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.contactName)
        private val number: TextView = itemView.findViewById(R.id.contactNumber)

        fun bind(item: ContactFromLocal) {
            name.text = item.name
            number.text = item.number
        }
    }

    class ContactsFromDeviceViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.contactNameFromDevice)
        private val number: TextView = itemView.findViewById(R.id.contactNumberFromDevice)

        fun bind(item: ContactFromDevice) {
            name.text = item.name
            number.text = item.number
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            ListItem.Type.CONTACT_FROM_LOCAL.value -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.contact_from_local_in_list, parent, false)
                return ContactsFromDBViewHolder(view)
            }

            ListItem.Type.CONTACT_FROM_DEVICE.value -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.contact_from_device_in_list, parent, false)
                return ContactsFromDeviceViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.contact_from_local_in_list, parent, false)
                return ContactsFromDBViewHolder(view)
            }
        }
    }

    fun setItems(newItems: List<ListItem>) {
        val diffCallback = ContactCallback(items, newItems)
        val diffCourses = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffCourses.dispatchUpdatesTo(this)
        notifyItemRangeChanged(0, newItems.size)
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (items[position].type == ListItem.Type.CONTACT_FROM_LOCAL.value) {
            val h = holder as ContactsFromDBViewHolder
            h.bind(items[position] as ContactFromLocal)
        } else if (items[position].type == ListItem.Type.CONTACT_FROM_DEVICE.value) {
            val h = holder as ContactsFromDeviceViewHolder
            h.bind(items[position] as ContactFromDevice)
        }
        holder.itemView.setOnClickListener {
            if (items[position].type == ListItem.Type.CONTACT_FROM_LOCAL.value) {
                val item = items[position] as ContactFromLocal
                val args = Bundle()
                item.id?.let { it1 -> args.putInt(ID_KEY, it1) }
                args.putString(NAME_KEY, item.name)
                args.putString(NUMBER_KEY, item.number)
                val transaction = fragmentManager
                transaction?.commit {
                    add<ContactFromLocalFragment>(R.id.nav_container, args = args)
                    addToBackStack(CONTACT_LIST_FRAGMENT_IN_BACK_STACK)
                }
            } else if (items[position].type == ListItem.Type.CONTACT_FROM_DEVICE.value) {
                val item = items[position] as ContactFromDevice
                val args = Bundle()
                args.putString(NAME_KEY, item.name)
                args.putString(NUMBER_KEY, item.number)
                val transaction = fragmentManager
                transaction?.commit {
                    add<ContactFromDeviceFragment>(R.id.nav_container, args = args)
                    addToBackStack(CONTACT_LIST_FRAGMENT_IN_BACK_STACK)
                }
            }

        }
    }

}