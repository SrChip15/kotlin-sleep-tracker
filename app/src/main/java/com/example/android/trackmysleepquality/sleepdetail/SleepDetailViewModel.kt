package com.example.android.trackmysleepquality.sleepdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight

class SleepDetailViewModel(
    nightId: Long,
    dataSource: SleepDatabaseDao
): ViewModel() {

    private val night = MediatorLiveData<SleepNight>()

    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    init {
        Log.i("SleepDetailVM", "ID: $nightId")
       night.addSource(dataSource.getNightWithId(nightId), night::setValue)
        Log.i("SleepDetailVM", "Night: ${night.value?.nightId}, ${night.value?.sleepQuality}")
    }

    fun getNight() = night

    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }

    fun onClose() {
        _navigateToSleepTracker.value = true
    }
}