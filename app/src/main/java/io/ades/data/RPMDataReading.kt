package io.ades.data

import android.provider.ContactsContract

data class RPMDataReading(
    val rpm : DataReading<Int>,
    val throttle : DataReading<Float>
)