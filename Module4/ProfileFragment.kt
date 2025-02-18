package com.example.module_4praticle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val textView = view.findViewById<TextView>(R.id.profile_text)

        // Receiving data from MainActivity
        val username = arguments?.getString("username", "Guest")
        textView.text = "Welcome, $username"

        return view
    }
}
