package com.example.lab7.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab7.presentation.ContactAdapter
import com.example.lab7.ContactsApplication
import com.example.lab7.R
import com.example.lab7.presentation.ContactsListViewState
import com.example.lab7.presentation.ContactsListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContactsListFragment() : Fragment() {
    companion object {
        const val CONTACT_LIST_FRAGMENT_IN_BACK_STACK = "Contact_list_Fragment"
    }

    private val viewModel: ContactsListViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            (requireContext().applicationContext as ContactsApplication).appComponent.viewModelsFactory()
        ).get(ContactsListViewModel::class.java)
    }

    private lateinit var adapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacts_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView = view.findViewById(R.id.contactList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ContactAdapter(fragmentManager)

        viewModel.loadContacts()
        viewModel.state.observe(viewLifecycleOwner) { newState ->
            renderState(newState)
        }
        recyclerView.adapter = adapter

        val button = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        button.setOnClickListener {
            val transaction = fragmentManager
            transaction?.commit {
                add<ContactFromLocalFragment>(R.id.nav_container)
                addToBackStack(CONTACT_LIST_FRAGMENT_IN_BACK_STACK)
            }
        }
    }

    private fun renderState(state: ContactsListViewState) {
        when (state) {
            ContactsListViewState.Loading -> {
                Toast.makeText(this.requireContext(), "loading", Toast.LENGTH_SHORT).show()
            }

            is ContactsListViewState.Success -> {
                adapter.setItems(state.contacts)
            }
        }
    }
}