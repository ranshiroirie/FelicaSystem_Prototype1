package com.ranshiroirie.felicaserverconnect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ranshiroirie.felicaserverconnect.felica.device.FelicaDeviceRepository

class MainViewModelFactory (private val felicaDeviceRepo: FelicaDeviceRepository)
    :ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(felicaDeviceRepo) as T
    }
}