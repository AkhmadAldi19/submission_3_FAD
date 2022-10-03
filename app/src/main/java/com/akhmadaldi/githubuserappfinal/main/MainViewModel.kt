package com.akhmadaldi.githubuserappfinal.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akhmadaldi.githubuserappfinal.data.api.ApiConfig
import com.akhmadaldi.githubuserappfinal.data.response.ItemItems
import com.akhmadaldi.githubuserappfinal.data.response.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val listUser = MutableLiveData<ArrayList<ItemItems>>()

    fun setSearchUser(query: String){
        ApiConfig.apiInstace
            .getSearchUsers(query)
            .enqueue(object : Callback<UsersResponse> {
                override fun onResponse(
                    call: Call<UsersResponse>,
                    response: Response<UsersResponse>
                ) {
                    if (response.isSuccessful){
                        listUser.postValue(response.body()?.items as ArrayList<ItemItems>)
                    }
                }

                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    Log.e("Failure", t.message.toString())
                }
            })
    }

    fun getSearchUser(): LiveData<ArrayList<ItemItems>> {
        return listUser
    }
}