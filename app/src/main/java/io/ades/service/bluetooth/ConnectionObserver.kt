package io.ades.service.bluetooth

import android.bluetooth.BluetoothSocket

import com.polidea.rxandroidble2.RxBleDevice

interface ConnectionObserver {
    fun onConnectionSuccess(device: BluetoothSocket)
    fun onConnectionFail()
    fun onDisconnected(device: RxBleDevice)
}