package io.ades.service.bluetooth

interface DeviceSignalsListener {

    fun onStartListeningSuccess()
    fun onStartListeningFail()
    fun onSignal(data: ByteArray?)

}