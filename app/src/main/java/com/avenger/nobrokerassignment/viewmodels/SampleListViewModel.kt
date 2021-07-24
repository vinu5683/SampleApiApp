package com.avenger.nobrokerassignment.viewmodels

import android.util.Log
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
            try {
                val data = sampleRepository.getAllResponse()
                if (data.value != null) {
                    emit(data.value!!)
                }
            } catch (e: Exception) {
                Log.d("TAG", "getAllResponse: No Internet")
            }
        }
    }

    fun getMyList(): LiveData<List<SampleEntity>>? {
        try {
            return sampleRepository.getResponseList()
        } catch (e: Exception) {
            return null
        }
    }

    fun setSampleEntityObj(sampleEntity: SampleEntity) {
        sampleRepository.setSampleEntityObj(sampleEntity)
    }

    fun getSampleEntityObj(): SampleEntity {
        return sampleRepository.getSampleEntityObj()
    }

    fun searchByText(trim: String): LiveData<List<SampleEntity>>? {
        return sampleRepository.getMatchingResponse(trim)
    }

}