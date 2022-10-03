package com.akhmadaldi.githubuserappfinal.themes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akhmadaldi.githubuserappfinal.data.preferences.SettingPreferences
import java.lang.IllegalArgumentException

class ThemeViewModelFactory(private val preference: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(preference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }
}