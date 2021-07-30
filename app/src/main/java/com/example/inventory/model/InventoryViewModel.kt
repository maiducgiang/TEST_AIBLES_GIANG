

package com.example.inventory

import android.util.Log
import androidx.lifecycle.*
import com.example.inventory.data.User
import com.example.inventory.data.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

//lateinit var resultDemo1: List<User>
class InventoryViewModel(private val userDao: UserDao) : ViewModel() {

    val allUsers: LiveData<List<User>> = userDao.getUsers().asLiveData()

    fun isStockAvailable(user: User): Boolean {
        return true
    }

    fun updateUser(
        id: Int,
        userLogin: String,
        userType: String,
        userUrl: String
    ) {
        val updatedUser = getUpdatedUserEntry(id, userLogin, userType, userUrl)
        updateUser(updatedUser)
    }
    fun getUser(id: Int): Flow<User> {
        return userDao.getUser(id)
    }
    private fun updateUser(user: User) {
        viewModelScope.launch {
            userDao.update(user)
        }
    }
    fun sellUser(user: User) {
        updateUser(user)
    }
    fun addNewUser(userLogin: String,
                   userType: String,
                   userUrl: String) {
        //getAllUser()
        val newUser = getNewUserEntry(userLogin, userType, userUrl)
        insertUser(newUser)
    }
    private fun insertUser(user: User) {
        Log.d("check", "insert")
        viewModelScope.launch {
            userDao.insert(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            userDao.delete(user)
        }
    }
    fun retrieveUser(id: Int): LiveData<User> {
        return userDao.getUser(id).asLiveData()
    }

    fun isEntryValid(userLogin: String,
                     userType: String,
                     userUrl: String): Boolean {
        if (userLogin.isBlank() || userType.isBlank() || userUrl.isBlank()) {
            return false
        }
        return true
    }
    private fun getNewUserEntry(userLogin: String,
                                userType: String,
                                userUrl: String): User {
        return User(
            userLogin = userLogin,
            userType = userType,
            userUrl = userUrl
        )
    }

    private fun getUpdatedUserEntry(
        id: Int,
        userLogin: String,
        userType: String,
        userUrl: String
    ): User {
        return User(
            id = id,
            userLogin = userLogin,
            userType = userType,
            userUrl = userUrl
        )
    }
    //Service
    //
    /*
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
    }*/
}

class InventoryViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

