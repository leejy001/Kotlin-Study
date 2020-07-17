package com.example.communication

import retrofit2.Call
import retrofit2.http.GET

interface UserApi {
    @GET("/api/lee")
    fun  getUser(): Call<User>
}