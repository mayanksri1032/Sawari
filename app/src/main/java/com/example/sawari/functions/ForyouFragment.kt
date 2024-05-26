package com.example.sawari.functions

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.sawari.R

class ForyouFragment : Fragment() {
    // Define any required parameters for the fragment
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_foryou, container, false)

        // Find the ImageView by its ID
        val backButton: ImageView = view.findViewById(R.id.backtr)

        // Set OnClickListener for the ImageView
        backButton.setOnClickListener {
            // Handle the back button click event to redirect to DropActivity
            val intent = Intent(requireActivity(), DropActivity::class.java)
            startActivity(intent)
            requireActivity().finish() // Optional: call finish() to close the current activity
        }

        return view
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        // Create a new instance of the fragment with provided parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ForyouFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
