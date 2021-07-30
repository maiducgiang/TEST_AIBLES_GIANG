package com.example.inventory.internet

import com.example.inventory.data.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @GET("users")
    suspend fun getAllUsers(): List<User>

    @POST("user")
    suspend fun createUser(@Body user: User)
}