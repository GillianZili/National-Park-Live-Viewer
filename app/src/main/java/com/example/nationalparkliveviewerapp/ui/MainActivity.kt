package com.example.nationalparkliveviewerapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nationalparkliveviewerapp.R
import com.example.nationalparkliveviewerapp.network.ApiService
import com.example.nationalparkliveviewerapp.network.ParkBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader


class MainActivity : ComponentActivity() {
    val parkModels = ArrayList<ParkModel>()
    private var apiKey: String = ""
    private val TAG: String = "Check_response"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            val inputStream = assets.open("api_key.json")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            var line: String?
            while ((reader.readLine().also { line = it }) != null) {
                stringBuilder.append(line)
            }
            val jsonObject = JSONObject(stringBuilder.toString())
            apiKey = jsonObject.getString("API_KEY")
            Log.i(TAG, "get api_key succeeded")
        } catch (e: Exception) {
            Log.i(TAG, "get api_key failed: ${e.message}")
        }

        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.mRecyclerView)
        getParkInfo(recyclerView)
        val adapter = PK_RecyclerViewAdapter(this, parkModels)
        recyclerView.setAdapter(adapter)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun getParkInfo(recyclerView: RecyclerView) {
        Log.i(TAG, "getParkInfo called")
        try {
            val api = Retrofit.Builder()
                .baseUrl("https://developer.nps.gov/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)

            api.getWebcams(apiKey, 999).enqueue(object : Callback<ParkBody> {
                override fun onResponse(
                    call: Call<ParkBody>,
                    response: Response<ParkBody>
                ) {
                    if (response.isSuccessful) {
                        Log.i(TAG, "API Success: ${response.body()}")
                        response.body()?.data?.let {
                            for (cam in it) {
                                if (cam.isStreaming) {
                                    val park = ParkModel(
                                        "unknown",
                                        "Unknown",
                                        "Unknown",
                                        "Unknown",
                                        "Unknown"
                                    )
                                    if (cam.relatedParks.size > 0) {
                                        val parkLocation = cam.relatedParks.get(0)
                                        val parkImage: String = if (cam.images.isNotEmpty()) {
                                            cam.images[0].url
                                        } else {
                                            ""
                                        }
                                        park.parkName = parkLocation.fullName
                                        park.parkLocation = parkLocation.states
                                        park.title = cam.title
                                        park.imageUrl = parkImage
                                        park.webcamUrl = cam.url
                                        Log.i(TAG, "Add park: ${cam.toString()}")
                                        parkModels.add(park)
                                    } else {
                                        Log.i(TAG, "Got a bad park: ${cam.toString()}")
                                    }
                                }

                            }
                            Log.i(TAG, "webcam num = : ${parkModels.size}")
                            recyclerView.adapter?.notifyDataSetChanged()
                        }
                    } else {
                        Log.i(TAG, "No ${response.raw()}")
                    }
                }

                override fun onFailure(call: Call<ParkBody>, t: Throwable) {
                    Log.e(TAG, "API Failure: ${t.message} ${call.toString()}")
                }
            })
        } catch (e: Exception) {
            Log.i(TAG, "what ${e.message}")
        }
    }
}