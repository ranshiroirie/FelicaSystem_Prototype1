package com.ranshiroirie.felicaserverconnect.felica.device

import android.os.Build
import android.util.Log
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope

class FelicaDeviceRepository(private val felicaDeviceApi: FelicaDeviceClientApi) {
    fun getFelicaDeviceListRepo(): Single<List<FelicaDevice>>{
        return felicaDeviceApi.getFelicaDeviceList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.body()
                return@map it.body()
                    ?: throw IOException("No Device")
            }
    }

    fun getFelicaDeviceByCardInfoRepo(
        card_id: String,
        card_pmm: String,
        card_sys: String
    ):Single<FelicaDevice>{
        return felicaDeviceApi.getFelicaDeviceByCardInfo(card_id, card_pmm, card_sys)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                return@map it.body()
            }
    }

    fun getFelicaDeviceByIdRepo(felica_id: Int):Single<FelicaDevice>{
        return felicaDeviceApi.getFelicaDeviceById(felica_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                return@map it.body()
            }
    }

    fun createFelicaDeviceRepo(
        device_name: String,
        card_id: String,
        card_pmm: String,
        card_sys: String
    ):Single<FelicaDevice>{
        return felicaDeviceApi.createFelicaDevice(device_name, card_id, card_pmm, card_sys, Build.MODEL)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                return@map it.body()
            }
    }

    fun updateFelicaDeviceNameByIdRepo(
        felica_id: Int,
        device_name: String,
    ):Single<FelicaDevice>{
        return felicaDeviceApi.updateFelicaDeviceNameById(felica_id, device_name, Build.MODEL)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                return@map it.body()
            }
    }

    fun deleteFelicaDeviceByIdRepo(
        felica_id: Int
    ):Single<Void>{
        return felicaDeviceApi.deleteFelicaDeviceById(felica_id, Build.MODEL)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                return@map it.body()
            }
    }

}