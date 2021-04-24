package com.ranshiroirie.felicaserverconnect.felica.device

object FelicaDeviceRepoFactory {

    fun createFelicaDeviceRepository(): FelicaDeviceRepository {
        val felicaDeviceClientApi = RestUtil.retrofit.create(FelicaDeviceClientApi::class.java)
        return FelicaDeviceRepository(felicaDeviceClientApi)
    }
}