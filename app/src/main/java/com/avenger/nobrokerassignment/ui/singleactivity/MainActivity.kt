package com.avenger.nobrokerassignment.ui.singleactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProviders
import com.avenger.nobrokerassignment.application.MyApplication
import com.avenger.nobrokerassignment.databinding.ActivityMainBinding
import com.avenger.nobrokerassignment.repository.SampleRepository
import com.avenger.nobrokerassignment.util.GetSampleViewModel
import com.avenger.nobrokerassignment.viewmodels.SampleListViewModel
import com.avenger.nobrokerassignment.viewmodels.SampleListViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeNavigationController()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        initializeTheRoom()

        //initializing the interface for getting viewmodel
    }

    private fun initializeNavigationController() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    //TODO: Initialize RoomDatabase.
    private fun initializeTheRoom() {

    }

    companion object {
        lateinit var getSampleViewModel: GetSampleViewModel
    }


}