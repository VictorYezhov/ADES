package io.ades.service.bluetooth

import android.app.Application
import com.polidea.rxandroidble2.RxBleDevice

interface BluetoothService {

    fun init(application : Application)

    fun checkBluetooth() : Boolean

    fun scanForDevices(onDeviceFound : (foundDevice: RxBleDevice)-> Unit)

    fun cancelScan()

    fun connectToDevice(device: RxBleDevice, connectionObserver: ConnectionObserver)
}