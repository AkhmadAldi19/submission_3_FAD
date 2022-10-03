package com.akhmadaldi.githubuserappfinal.data.response

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("items")
    val items: List<ItemItems?>? = null
)
data class ItemItems (
    val login: String,
    val id: Int,
    val avatar_url: String
)