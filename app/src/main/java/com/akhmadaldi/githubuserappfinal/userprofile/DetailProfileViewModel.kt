package com.akhmadaldi.githubuserappfinal.userprofile

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhmadaldi.githubuserappfinal.data.api.ApiConfig
import com.akhmadaldi.githubuserappfinal.data.local.UserDatabase
import com.akhmadaldi.githubuserappfinal.data.local.UserFavorite
import com.akhmadaldi.githubuserappfinal.data.local.UserFavoriteDAO
import com.akhmadaldi.githubuserappfinal.data.response.DetailUsersResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailProfileViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUsersResponse>()

    private var userDAO: UserFavoriteDAO?
    private var userDB: UserDatabase?

    init {
        userDB = UserDatabase.getDatabase(application)
        userDAO = userDB?.userFavoriteDAO()
    }

    fun setUserDetail(username: String) {
        ApiConfig.apiInstace
            .getDetailUsers(username)
            .enqueue(object : Callback<DetailUsersResponse> {
                override fun onResponse(
                    call: Call<DetailUsersResponse>,
                    response: Response<DetailUsersResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUsersResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }


    fun getUserDetail(): LiveData<DetailUsersResponse> {
        return user
    }

    fun addToFavorite(username: String, id: Int, avatar: String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = UserFavorite(
                username,
                id,
                avatar
            )
            userDAO?.addToFavorite(user)
        }
    }

    fun checkUser(id: Int) = userDAO?.checkUser(id)

    fun removeUser(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDAO?.removeFromFavorit(id)
        }
    }
}