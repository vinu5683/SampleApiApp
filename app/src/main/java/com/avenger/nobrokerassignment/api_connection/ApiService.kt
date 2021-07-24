package com.avenger.nobrokerassignment.api_connection

import com.avenger.nobrokerassignment.datamodels.SampleResponse
import retrofit2.http.GET

interface ApiService {

//    @GET("b/60fa8fefa917050205ce5470")
    @GET("v1/28bde2da-71bc-469d-9414-63ae2119bb32")
    suspend fun hitApi(): ArrayList<SampleResponse>

}