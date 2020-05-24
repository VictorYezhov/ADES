package io.ades.service.device

import android.bluetooth.BluetoothSocket
import io.ades.data.DataReading
import io.ades.data.RPMDataReading
import io.reactivex.Observable
import java.util.*

interface DeviceManager {
    fun setTarget(socket : BluetoothSocket)
    fun getPRM()
    fun getThrottlePos()
    fun getRPMCarState()

    fun getStateReading() : Observable<RPMDataReading>
}