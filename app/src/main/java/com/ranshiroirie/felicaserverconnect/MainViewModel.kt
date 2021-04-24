package com.ranshiroirie.felicaserverconnect

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.ranshiroirie.felicaserverconnect.felica.device.FelicaDevice
import com.ranshiroirie.felicaserverconnect.felica.device.FelicaDeviceRepository
import kotlinx.coroutines.*

class MainViewModel(private val felicaDeviceRepo: FelicaDeviceRepository) : ViewModel() {
    private val _felicaDeviceList: MutableLiveData<List<FelicaDevice>> = MutableLiveData()
    val felicaDeviceList: LiveData<List<FelicaDevice>> = _felicaDeviceList
    val progressbarFrag: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val connectErrorFrag: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    @SuppressLint("CheckResult")
    fun getFelicaDeviceList(){
        progressbarFrag.value = true
        felicaDeviceRepo.getFelicaDeviceListRepo()
            .subscribe ({ felicaDeviceList:List<FelicaDevice> ->
                progressbarFrag.value = false
                _felicaDeviceList.postValue(felicaDeviceList)
            },{
                connectErrorFrag.value = true
                progressbarFrag.value = false
                Log.e("FelicaDeviceClientApi",it.message.toString())
            })
        connectErrorFrag.value = false
    }


    @SuppressLint("CheckResult")
    fun deleteFelicaDevice(felica_id: Int){
        felicaDeviceRepo.deleteFelicaDeviceByIdRepo(felica_id).subscribe(
            {}, {
                Log.e("FelicaDeviceClientApi",it.message.toString())
            }
        )

    }
}