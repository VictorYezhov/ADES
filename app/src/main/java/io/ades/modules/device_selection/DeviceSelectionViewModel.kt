package io.ades.modules.device_selection

import android.bluetooth.BluetoothSocket
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polidea.rxandroidble2.RxBleDevice
import io.ades.inject
import io.ades.service.bluetooth.BluetoothService
import io.ades.service.bluetooth.ConnectionObserver
import io.ades.service.device.DeviceManager

class DeviceSelectionViewModel : ViewModel() {

    private val bluetoothService : BluetoothService by inject()
    private val deviceManager : DeviceManager by inject()
    val connectionSuccess = MutableLiveData<Boolean>()
    val devices = MutableLiveData<RxBleDevice>()

    init {
        bluetoothService.scanForDevices{device ->
            devices.value = device
        }
    }



    fun connect(rxBleDevice: RxBleDevice){
        bluetoothService.connectToDevice(rxBleDevice, object : ConnectionObserver{
            override fun onConnectionSuccess(device: BluetoothSocket) {
                deviceManager.setTarget(device)
                connectionSuccess.postValue( true)
            }

            override fun onConnectionFail() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDisconnected(device: RxBleDevice) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }
}
