package com.avenger.nobrokerassignment.repository

import androidx.lifecycle.LiveData
import com.avenger.nobrokerassignment.api_connection.ApiService
import com.avenger.nobrokerassignment.api_connection.Network
import com.avenger.nobrokerassignment.datamodels.SampleResponse
import com.avenger.nobrokerassignment.localdatabase.SampleDAO
import com.avenger.nobrokerassignment.localdatabase.SampleEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SampleRepository(val sampleDAO: SampleDAO) {

    var sampleEntity: SampleEntity

    init {
        sampleEntity = SampleEntity("", "", "")
    }

    suspend fun getAllResponse(): LiveData<List<SampleEntity>> {
        val apiClient = Network.getInstance().create(ApiService::class.java)
        var result = apiClient.hitApi()
        return addToRoomDatabase(result)
    }

    private fun addToRoomDatabase(result: ArrayList<SampleResponse>): LiveData<List<SampleEntity>> {
        if (result.size > 0) {
            sampleDAO.removeAll();
        }
        for (i in 0 until result.size) {
            val sampleEntity = SampleEntity(result[i].image, result[i].subTitle, result[i].title)
            insertResponse(sampleEntity)
        }
        return getResponseList()
    }

    fun insertResponse(sampleEntity: SampleEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            sampleDAO.addEntry(sampleEntity)
        }
    }


    fun getResponseList(): LiveData<List<SampleEntity>> {
        return sampleDAO.getMySampleList()
    }


    fun setSampleEntityObj(sampleEntity: SampleEntity) {
        this.sampleEntity = sampleEntity
    }

    fun getSampleEntityObj(): SampleEntity {
        return sampleEntity
    }

    fun getMatchingResponse(trim: String): LiveData<List<SampleEntity>>? {
        return sampleDAO.searchResponse("%$trim%")
    }

}