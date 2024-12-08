package com.example.lab7.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.lab7.R

class ContactFromDeviceFragment : Fragment() {
    companion object {
        const val NAME_KEY = "name"
        const val NUMBER_KEY = "number"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cotact_from_device, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = arguments
        val name = view.findViewById<TextView>(R.id.nameNotEditable)
        val number = view.findViewById<TextView>(R.id.phoneNotEditable)
        name.text = bundle?.getString(NAME_KEY)
        number.text = bundle?.getString(NUMBER_KEY)
    }
}