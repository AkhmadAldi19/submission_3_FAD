package com.akhmadaldi.githubuserappfinal.userprofile.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akhmadaldi.githubuserappfinal.data.api.ApiConfig
import com.akhmadaldi.githubuserappfinal.data.response.ItemItems
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel(){
    val listFollowers = MutableLiveData<ArrayList<ItemItems>>()

    fun setListFollowing(username: String){
        ApiConfig.apiInstace
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<ItemItems>> {
                override fun onResponse(
                    call: Call<ArrayList<ItemItems>>,
                    response: Response<ArrayList<ItemItems>>
                ) {
                    if (response.isSuccessful){
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<ItemItems>>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getListFollowing(): LiveData<ArrayList<ItemItems>> {
        return listFollowers
    }
}