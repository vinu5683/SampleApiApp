package com.avenger.nobrokerassignment.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avenger.nobrokerassignment.R
import com.avenger.nobrokerassignment.ui.singleactivity.MainActivity


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private lateinit var v: View
    val sampleViewModel = MainActivity.getSampleViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v = view;

        initializeTheViewsAndListeners()

        showDataOnRecyclerView()
    }

    private fun initializeTheViewsAndListeners() {

    }

    private fun showDataOnRecyclerView() {

    }

}