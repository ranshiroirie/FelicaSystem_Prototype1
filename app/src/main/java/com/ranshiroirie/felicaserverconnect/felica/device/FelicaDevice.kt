package com.ranshiroirie.felicaserverconnect.felica.device

import java.util.*

data class FelicaDevice(
    var felica_id: Int,
    var device_name: String,
    var card_id: String,
    var card_pmm: String,
    var card_sys: String,
    var registered_datetime: Date
)
