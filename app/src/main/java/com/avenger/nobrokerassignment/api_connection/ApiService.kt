package com.avenger.nobrokerassignment.api_connection

import com.avenger.nobrokerassignment.datamodels.SampleResponse
import retrofit2.http.GET

interface ApiService {

    @GET("b/60fa8fefa917050205ce5470")
    suspend fun hitApi(): ArrayList<SampleResponse>

}