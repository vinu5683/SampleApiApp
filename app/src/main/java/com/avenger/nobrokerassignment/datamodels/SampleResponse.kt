package com.avenger.nobrokerassignment.datamodels

import com.google.gson.annotations.SerializedName

data class SampleResponse(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("subTitle")
	val subTitle: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)