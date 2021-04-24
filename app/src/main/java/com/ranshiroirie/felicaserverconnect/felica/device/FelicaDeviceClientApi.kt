package com.ranshiroirie.felicaserverconnect.felica.device

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex

interface FelicaDeviceClientApi {

    @GET("felica_device/get/all")
    fun getFelicaDeviceList():Single<Response<List<FelicaDevice>>>

    @GET("felica_device/get/card_info")
    fun getFelicaDeviceByCardInfo(
        @Query ("card_id")card_id: String,
        @Query ("card_pmm")card_pmm: String,
        @Query ("card_sys") card_sys: String
    ):Single<Response<FelicaDevice>>

    @GET("felica_device/get/felica_id")
    fun getFelicaDeviceById(
        @Query("felica_id")felica_id: Int
    ):Single<Response<FelicaDevice>>

    @POST("felica_device/add")
    fun createFelicaDevice(
        @Query ("device_name")device_name: String,
        @Query ("card_id")card_id: String,
        @Query ("card_pmm")card_pmm: String,
        @Query ("card_sys") card_sys: String,
        @Query ("operation_device") operation_device: String
    ):Single<Response<FelicaDevice>>

    @PUT("felica_device/update/device_name")
    fun updateFelicaDeviceNameById(
        @Query("felica_id")felica_id: Int,
        @Query ("device_name")device_name: String,
        @Query ("operation_device") operation_device: String
    ):Single<Response<FelicaDevice>>

    @DELETE("felica_device/delete/felica_id")
    fun deleteFelicaDeviceById(
        @Query("felica_id")felica_id: Int,
        @Query ("operation_device") operation_device: String
    ):Single<Response<Void>>

}