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

class MainActivity : AppCompatActivity(), GetSampleViewModel {
    private lateinit var binding: ActivityMainBinding

    private lateinit var appClass: MyApplication
    private lateinit var repository: SampleRepository
    private lateinit var sampleViewModel: SampleListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeNavigationController()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        initializeTheRoom()

        //initializing the interface for getting viewmodel
        getSampleViewModel = this
    }

    private fun initializeNavigationController() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    //TODO: Initialize RoomDatabase.
    private fun initializeTheRoom() {
        appClass = application as MyApplication
        repository = appClass.repository
        val viewModelFactory = SampleListViewModelFactory(repository)
        sampleViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SampleListViewModel::class.java)
    }

    companion object {
        lateinit var getSampleViewModel: GetSampleViewModel
    }

    override fun getSampleViewModel(): SampleListViewModel {
        return sampleViewModel
    }
}