package com.avenger.nobrokerassignment.application

import android.app.Application
import com.avenger.nobrokerassignment.localdatabase.SampleDatabase
import com.avenger.nobrokerassignment.repository.SampleRepository

class MyApplication() : Application() {

    val sampleDao by lazy {
        val roomDatabase = SampleDatabase.getInstance(this)
        roomDatabase.getSampleDAO()
    }

    val repository by lazy {
        SampleRepository(sampleDao)
    }

}