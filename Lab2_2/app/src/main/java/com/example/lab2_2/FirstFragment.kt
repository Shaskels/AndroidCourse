package com.example.lab2_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.add
import androidx.fragment.app.commit

class FirstFragment : Fragment() {

    companion object{
        const val FIRST_FRAGMENT_IN_BACK_STACK = "Fragment1"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_first, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val nextButton : Button = view.findViewById(R.id.button)
        nextButton.setOnClickListener{
            val transaction = fragmentManager
            transaction?.commit{
                add<SecondFragment>(R.id.navigation_container)
                addToBackStack(FIRST_FRAGMENT_IN_BACK_STACK)
            }
        }
    }
}