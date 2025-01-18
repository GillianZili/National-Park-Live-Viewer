package com.example.nationalparkliveviewerapp

import android.os.Bundle
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
import androidx.compose.foundation.layout.fillMaxSize as fillMaxSize

class MainActivity : ComponentActivity() {
    val parkModels = ArrayList<ParkModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView:RecyclerView = findViewById(R.id.mRecyclerView)
        setUpParkModels()
        val adapter = PK_RecyclerViewAdapter(this,parkModels)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
    private fun setUpParkModels(){
        val parkNames = resources.getStringArray(R.array.park_name)
        val parkLocations = resources.getStringArray(R.array.state_name)
        for(i in parkNames.indices){
            parkModels.add(ParkModel(R.drawable.bg_compose_background,parkLocations[i],parkNames[i]))
        }
    }
}

@Composable
public fun ParkTable(header:String,modifier:Modifier=Modifier){
    val image = painterResource(R.drawable.bg_compose_background)
    Column(modifier) {
        Image(
            painter=image,
            contentDescription = null
        )
        Title(
            header=header,
        )
    }
}
@Composable
public fun Title(header:String,modifier: Modifier = Modifier){
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier)
    {
        Text(
            text=header,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                start = 10.dp,
                top = 20.dp,
                end = 15.dp,
                bottom = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    NationalParkLiveViewerAppTheme {
        ParkTable(header="National Park Live Viewer Stream")
    }
}