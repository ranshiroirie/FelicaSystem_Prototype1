package com.ranshiroirie.felicaserverconnect.ui.reader

import android.annotation.SuppressLint
import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ranshiroirie.felicaserverconnect.felica.device.FelicaDevice
import com.ranshiroirie.felicaserverconnect.felica.device.FelicaDeviceRepository
import com.ranshiroirie.felicaserverconnect.felica.reader.ReadDevice
import java.text.SimpleDateFormat
import java.util.*

class ReaderViewModel(private val felicaDeviceRepo: FelicaDeviceRepository) : ViewModel() {
    private var _readDevice :MutableLiveData<ReadDevice> = MutableLiveData()
    private var _hitFelicaDevice :MutableLiveData<FelicaDevice> = MutableLiveData()
    private var _inputtedDeviceName : MutableLiveData<String> = MutableLiveData()
    val readDevice: LiveData<ReadDevice> = _readDevice
    val hitFelicaDevice: LiveData<FelicaDevice> = _hitFelicaDevice
    val inputtedDeviceName: MutableLiveData<String> = _inputtedDeviceName
    val getResultReg: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val checkRegistered: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val isEmptyDeviceName: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    init{
        initValue()
    }

    fun setReadDevice(setReadDevice: ReadDevice){
        initValue()
        _readDevice.value = setReadDevice
        checkFelicaDeviceIsRegistered(setReadDevice)
    }

    @SuppressLint("CheckResult")
    private fun checkFelicaDeviceIsRegistered(setReadDevice: ReadDevice){
        getResultReg.value = false
        checkRegistered.value = false
        felicaDeviceRepo.getFelicaDeviceByCardInfoRepo(
            setReadDevice.card_id,
            setReadDevice.card_pmm,
            setReadDevice.card_sys
        ).subscribe(
            {
                _hitFelicaDevice.postValue(it)
                checkRegistered.value = true
                getResultReg.value = true
            },{
                Log.e("FelicaDeviceClientApi",it.message.toString())
                getResultReg.value = true
            })

    }

    @SuppressLint("CheckResult")
    fun registerFelicaDevice(){
        isEmptyDeviceName.value = false
        if (inputtedDeviceName.value.toString().isNotEmpty()){
            felicaDeviceRepo.createFelicaDeviceRepo(
                inputtedDeviceName.value.toString(),
                readDevice.value!!.card_id,
                readDevice.value!!.card_pmm,
                readDevice.value!!.card_sys,
            ).subscribe(
                {
                    _hitFelicaDevice.postValue(it)
                    checkRegistered.value = true
                },{
                    Log.e("FelicaDeviceClientApi",it.cause.toString())
                    Log.e("FelicaDeviceClientApi",it.message.toString())
                }
            )
        }else{
            Log.e("readDeviceValue", "Value is null")
        }
    }

    private fun initValue(){
        _readDevice.value = ReadDevice("","","")
        _hitFelicaDevice.value = FelicaDevice(0,"","","","", Date(0))
        _inputtedDeviceName.value = ""
    }

    fun convertDateToStr(date:Date):String{
        val df = SimpleDateFormat("yyyy-MM-dd")
        return df.format(date)
    }
}