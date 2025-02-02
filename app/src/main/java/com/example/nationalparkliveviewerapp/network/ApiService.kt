package com.example.nationalparkliveviewerapp.network
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("webcams")
    fun getWebcams(
        @Query("api_key") apiKey:String,
        @Query("limit") limit:Int,
    ): Call<ParkBody>

}