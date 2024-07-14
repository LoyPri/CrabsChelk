package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.pm.ShortcutInfoCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.ui.theme.MyApplicationTheme
import org.json.JSONObject

const val API_KEY = "dab3af44de7d24ae7ff86549334e45bd"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "London",
                        modifier = Modifier.padding(innerPadding),
                        context = this
                    )
                }
            }
        }
}
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, context: Context) {
    val state = remember {
        mutableStateOf(value = "Unknown")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            Text(text = "Temp in $name = ${state.value} C")//при изменении state перерисовываем
        }
        Box(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(onClick = {
                getResult(name, state, context)
            }, modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
            ) {
                Text(text = "Refresh")
            }

        }
    }
}
private fun getResult(city: String, state: MutableState<String>, context: Context){
    val url = "https://api.openweathermap.org/data/2.5/weather" +
            "?appid=$API_KEY&" +
            "q=$city" +
            "&lang=ru" +
            "&units=metric"

    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        {
            response ->
            val obj = JSONObject(response)
            state.value = obj.getJSONObject("main").getString("temp")
        },
        {
            error ->
            Log.d("MyLog","Error $error")
        }
    )
    queue.add(stringRequest)
}
