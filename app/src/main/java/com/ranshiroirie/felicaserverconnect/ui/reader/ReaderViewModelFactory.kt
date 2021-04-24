package com.ranshiroirie.felicaserverconnect.ui.reader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ranshiroirie.felicaserverconnect.felica.device.FelicaDeviceRepository

class ReaderViewModelFactory (private val felicaDeviceRepo: FelicaDeviceRepository)
    :ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ReaderViewModel(felicaDeviceRepo) as T
    }
}