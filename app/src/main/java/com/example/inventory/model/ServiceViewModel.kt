package com.example.inventory.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.User
import com.example.inventory.data.UserDao
import com.example.inventory.internet.UserService
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceViewModel: ViewModel() {
    init {
        getAllUser()
    }
    private lateinit var  userDao: UserDao
    private fun insertUser(user: User) {
        Log.d("check", "insert")
        viewModelScope.launch {
            userDao.insert(user)
        }
    }
    val logging = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
    val service = retrofit.create(UserService::class.java)
    private lateinit var result: List<User>
    fun Result():List<User>{
        return result
    }
    fun getAllUser() {
        Log.d("check"," getAllUser")
        viewModelScope.async {
            Log.d("check ","size")
            result = service.getAllUsers()
            //resultDemo1 = result
            for (i in result){
                insertUser(i)
            }
        }
    }
}