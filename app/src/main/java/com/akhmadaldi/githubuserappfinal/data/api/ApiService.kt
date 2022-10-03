package com.akhmadaldi.githubuserappfinal.data.api

import com.akhmadaldi.githubuserappfinal.BuildConfig.KEYING
import com.akhmadaldi.githubuserappfinal.data.response.DetailUsersResponse
import com.akhmadaldi.githubuserappfinal.data.response.ItemItems
import com.akhmadaldi.githubuserappfinal.data.response.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
        @GET("search/users")
        @Headers("Authorization:$KEYING")
        fun getSearchUsers(
            @Query("q") query: String
        ): Call<UsersResponse>

        @GET("users/{username}")
        fun getDetailUsers(
            @Path("username") username: String
        ): Call<DetailUsersResponse>

        @GET("users/{username}/followers")
        fun getFollowers(
            @Path("username") username: String
        ): Call<ArrayList<ItemItems>>

        @GET("users/{username}/following")
        fun getFollowing(
            @Path("username") username: String
        ): Call<ArrayList<ItemItems>>
}