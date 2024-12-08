package com.example.lab7.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.lab7.ContactsApplication
import com.example.lab7.ui.ContactsListFragment.Companion.CONTACT_LIST_FRAGMENT_IN_BACK_STACK
import com.example.lab7.R
import com.example.lab7.presentation.ContactFromLocal
import com.example.lab7.presentation.ContactViewModel
import com.example.lab7.presentation.ContactViewState
import com.example.lab7.presentation.ContactsListViewModel

class ContactFromLocalFragment : Fragment() {

    companion object {
        const val ID_KEY = "id"
        const val NAME_KEY = "name"
        const val NUMBER_KEY = "number"
    }

    private val viewModelContacts: ContactsListViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            (requireContext().applicationContext as ContactsApplication).appComponent.viewModelsFactory()
        ).get(ContactsListViewModel::class.java)
    }

    private val viewModelContact: ContactViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            (requireContext().applicationContext as ContactsApplication).appComponent.viewModelsFactory()
        ).get(ContactViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = arguments
        val saveButton = view.findViewById<Button>(R.id.saveButton)
        val name = view.findViewById<EditText>(R.id.name)
        val number = view.findViewById<EditText>(R.id.phone)
        val id = bundle?.getInt(ID_KEY)
        name.setText(bundle?.getString(NAME_KEY))
        number.setText(bundle?.getString(NUMBER_KEY))

        saveButton.setOnClickListener {
            val contact = ContactFromLocal(id, name.text.toString(), number.text.toString())
            if (id != null)
                viewModelContact.updateContact(contact)
            else
                viewModelContact.appendContact(contact)
            viewModelContact.state.observe(viewLifecycleOwner) { newState ->
                renderState(newState)
            }
            val transaction = fragmentManager
            transaction?.popBackStack(
                CONTACT_LIST_FRAGMENT_IN_BACK_STACK,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
    }

    private fun renderState(state: ContactViewState) {
        when (state) {
            ContactViewState.Loading -> {
                Toast.makeText(this.requireContext(), "saving", Toast.LENGTH_SHORT).show()
            }

            is ContactViewState.Success -> {
                viewModelContacts.loadContacts()
            }
        }
    }
}