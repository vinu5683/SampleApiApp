package com.avenger.nobrokerassignment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.avenger.nobrokerassignment.localdatabase.SampleEntity
import com.avenger.nobrokerassignment.repository.SampleRepository
import com.avenger.nobrokerassignment.util.Resource
import kotlinx.coroutines.Dispatchers

class SampleListViewModel(private val sampleRepository: SampleRepository) : ViewModel() {


    fun getAllResponse(): LiveData<List<SampleEntity>> {
        return liveData(Dispatchers.IO) {
            val data = sampleRepository.getAllResponse()
            if (data.value!=null){
                emit(data.value!!)
            }
        }
    }

    fun getMyList(): LiveData<List<SampleEntity>> {
        return sampleRepository.getResponseList()
    }

}