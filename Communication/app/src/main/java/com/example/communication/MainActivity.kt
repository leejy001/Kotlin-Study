package com.example.communication

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textId = findViewById<TextView>(R.id.text_id)
        //응답 시간
        var okHttpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.0.77:3002")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        val api = retrofit.create(UserApi::class.java)

        var callGetUser = api.getUser()

        val buttonDeterUser: Button = findViewById(R.id.btn_deter_user)
        buttonDeterUser.setOnClickListener(View.OnClickListener {
            val callGetUser = api.getUser()
            callGetUser.enqueue(object : Callback<User>{
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("test", "실패 : $t")
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    Log.d("test", "성공 : ${response.raw()}")
                    textId.text = response.body()?.lee.toString()
                }
            })
        })
    }
}
