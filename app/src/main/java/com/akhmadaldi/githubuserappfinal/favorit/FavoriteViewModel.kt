package com.akhmadaldi.githubuserappfinal.favorit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.akhmadaldi.githubuserappfinal.data.local.UserDatabase
import com.akhmadaldi.githubuserappfinal.data.local.UserFavorite
import com.akhmadaldi.githubuserappfinal.data.local.UserFavoriteDAO

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDAO: UserFavoriteDAO?
    private var userDB: UserDatabase?

    init {
        userDB = UserDatabase.getDatabase(application)
        userDAO = userDB?.userFavoriteDAO()
    }

    fun getFavoriteUser(): LiveData<List<UserFavorite>>?{
        return userDAO?.getFavoriteUser()
    }
}