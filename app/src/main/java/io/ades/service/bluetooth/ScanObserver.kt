package io.ades.service.bluetooth


import com.polidea.rxandroidble2.RxBleDevice

interface ScanObserver {
    fun onNewDeviceFound(foundDevice: RxBleDevice)

}