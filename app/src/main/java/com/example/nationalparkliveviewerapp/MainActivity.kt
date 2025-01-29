package com.example.nationalparkliveviewerapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.nationalparkliveviewerapp.ui.theme.NationalParkLiveViewerAppTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nationalparkliveviewerapp.ui.theme.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.foundation.layout.fillMaxSize as fillMaxSize
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.TextView
import com.example.nationalparkliveviewerapp.ui.theme.ParkBody
import com.example.nationalparkliveviewerapp.ui.theme.Webcam
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    val parkModels = ArrayList<ParkModel>()

    private val apiKey = "E6dz83HRlNH9Pr1ES0mQxsTRkhfHJicwjpjogNcW"
    private val TAG: String ="Check_response"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)
        val recyclerView:RecyclerView = findViewById(R.id.mRecyclerView)
        getParkInfo(recyclerView)
        val adapter = PK_RecyclerViewAdapter(this,parkModels)
        recyclerView.setAdapter(adapter)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun getParkInfo(recyclerView:RecyclerView){
        Log.i(TAG, "getParkInfo called")
        try {
            // Code that may throw an exception
            val api = Retrofit.Builder()
                .baseUrl("https://developer.nps.gov/api/v1/")
            .addConverterFactory(GsonConverterFactory.create()) // 自动解析 JSON
            .build()
            .create(ApiService::class.java)

        api.getWebcams(apiKey,999).enqueue(object : Callback<ParkBody>{
            override fun onResponse(
                call: Call<ParkBody>,
                response: Response<ParkBody>
            ) {
                if (response.isSuccessful) {
                    Log.i(TAG, "API Success: ${response.body()}")
                    response.body()?.data?.let {
                        for(cam in it){
                            if(cam.isStreaming){
                                val park = ParkModel(R.drawable.bg_compose_background, "Unknown","Unknown")
                                if(cam.relatedParks.size>0){
                                    val ccc = cam.relatedParks.get(0)
                                    park.parkName = ccc.fullName
                                    park.parkLocation=ccc.states
                                    Log.i(TAG, "Add park: ${cam.toString()}")
                                    parkModels.add(park)
                                }else{
                                    Log.i(TAG, "Got a bad park: ${cam.toString()}")
                                }
                            }

                        }
                        Log.i(TAG, "webcam num = : ${parkModels.size}")
//                        for(park in parkModels){
//                            Log.i(TAG, "Final park = : ${park.parkName}")
//                        }
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                }else{
                    Log.i(TAG, "No ${response.raw()}")
                }
            }
            override fun onFailure(call: Call<ParkBody>, t: Throwable) {
                Log.e(TAG, "API Failure: ${t.message} ${call.toString()}")
            }
        })
        } catch (e: Exception) {
            // Code for handling the exception
            Log.i(TAG,"what ${e.message}")
        }

        Log.i(TAG, "api ok 1")
    }
}

//@Composable
//public fun ParkTable(header:String,modifier:Modifier=Modifier){
//    val image = painterResource(R.drawable.bg_compose_background)
//    Column(modifier) {
//        Image(
//            painter=image,
//            contentDescription = null
//        )
//        Title(
//            header=header,
//        )
//    }
//}
//@Composable
//public fun Title(header:String,modifier: Modifier = Modifier){
//    Column(
//        verticalArrangement = Arrangement.Center,
//        modifier = modifier)
//    {
//        Text(
//            text=header,
//            fontSize = 20.sp,
//            textAlign = TextAlign.Center,
//            modifier = Modifier.padding(
//                start = 10.dp,
//                top = 20.dp,
//                end = 15.dp,
//                bottom = 10.dp)
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun Preview() {
//    NationalParkLiveViewerAppTheme {
//        ParkTable(header="National Park Live Viewer Stream")
//    }
//}