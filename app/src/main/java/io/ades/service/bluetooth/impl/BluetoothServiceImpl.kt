package io.ades.service.bluetooth.impl

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.util.Log
import io.ades.service.bluetooth.BluetoothService
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.RxBleDevice
import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanSettings
import io.ades.inject
import io.ades.service.bluetooth.ConnectionObserver
import io.ades.service.bluetooth.PermissionService
import io.ades.service.bluetooth.ScanObserver
import io.ades.service.permissions.impl.PermissionServiceImpl
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class BluetoothServiceImpl(context : Context) : BluetoothService {

    companion object {
        private const val TAG = "BluetoothServiceImpl"
        private const val SERVICE_UUID = "0000FFE0-0000-1000-8000-00805F9B34FB"
        private const val CHARACTERISTIC_UUID = "0000FFE1-0000-1000-8000-00805F9B34FB"
    }

    private lateinit var rxBleClient :  RxBleClient
    private var disposable = CompositeDisposable()
    private val permissionService = PermissionServiceImpl()
    override fun init(application :Application) {
        rxBleClient = RxBleClient.create(application)

    }

    override fun checkBluetooth(): Boolean {
        Log.i(TAG, "checkBluetooth")
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val result = mBluetoothAdapter?.isEnabled ?: false
        Log.i(TAG, "checkBluetooth, result: $result")
        return result
    }

    override fun cancelScan() {

    }

    override fun scanForDevices(onDeviceFound : (foundDevice: RxBleDevice)-> Unit) {
        Log.i(TAG, "scanForDevices")
        if(!permissionService.hasBluetoothPermission() || !permissionService.hasLocationPermission()){
            disposable.add(permissionService
                .requestStartingPermissions()
                .subscribe{hasPermissionFromUser->
                    if(hasPermissionFromUser){
                        Log.i(TAG, "starting scan")
                        disposable.add(rxBleClient.scanBleDevices(ScanSettings.Builder().build(), ScanFilter.empty()).subscribe({ device->
                            Log.i(TAG, "ON DEVICE FOUND")
                            onDeviceFound(device.bleDevice)
                        },{
                            it.printStackTrace()
                        }) )
                    }else{
                        Log.i(TAG, "permissions declined")
                    }
                })
        }else{
            Log.i(TAG, "starting scan")
            disposable.add(rxBleClient.scanBleDevices(ScanSettings.Builder().build(), ScanFilter.empty()).subscribe({ device->
                Log.i(TAG, "ON DEVICE FOUND")
                onDeviceFound(device.bleDevice)
                disposable.clear()
            },{
                it.printStackTrace()
            }) )
        }
    }

    override fun connectToDevice(device: RxBleDevice, connectionObserver: ConnectionObserver) {

        disposable.add(device.establishConnection(false).subscribe({dataStream->

            val socket = device.bluetoothDevice.createInsecureRfcommSocketToServiceRecord(UUID.fromString("0000FFE1-0000-1000-8000-00805F9B34FB"))
            connectionObserver.onConnectionSuccess(socket)

        },{
            it.printStackTrace()
        }))
    }

}