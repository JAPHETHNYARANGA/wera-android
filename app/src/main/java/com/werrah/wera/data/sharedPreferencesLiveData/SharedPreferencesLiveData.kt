package com.werrah.wera.data.sharedPreferencesLiveData

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

class SharedPreferencesLiveData(private val sharedPreferences: SharedPreferences) : LiveData<Unit>(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onActive() {
        super.onActive()
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onInactive() {
        super.onInactive()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        // Notify observers when SharedPreferences change
        value = Unit
    }
}