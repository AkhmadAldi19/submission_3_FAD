package com.akhmadaldi.githubuserappfinal.userprofile.followers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akhmadaldi.githubuserappfinal.data.api.ApiConfig
import com.akhmadaldi.githubuserappfinal.data.response.ItemItems
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel(){
    val listFollowers = MutableLiveData<ArrayList<ItemItems>>()

    fun setListFollowers(username: String){
        ApiConfig.apiInstace
            .getFollowers(username)
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

    fun getListFollowers(): LiveData<ArrayList<ItemItems>> {
        return listFollowers
    }
}