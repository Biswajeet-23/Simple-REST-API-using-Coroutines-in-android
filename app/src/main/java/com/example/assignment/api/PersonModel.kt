package com.example.assignment.api
import com.google.gson.annotations.SerializedName

data class PersonModel(
    @SerializedName("country")
    var country : MutableList<Country> = mutableListOf(),
    @SerializedName("name")
    var name    : String
)

data class Country (

    @SerializedName("country_id"  )
    var countryId   : String,
    @SerializedName("probability" )
    var probability : String

)
