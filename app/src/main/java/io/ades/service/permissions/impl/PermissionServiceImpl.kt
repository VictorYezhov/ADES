package io.ades.service.permissions.impl

import android.Manifest
import androidx.core.content.PermissionChecker
import com.tbruyelle.rxpermissions2.RxPermissions
import io.ades.activity
import io.ades.context
import io.ades.service.bluetooth.PermissionService
import io.reactivex.Observable

class PermissionServiceImpl : PermissionService {
    private val rxPermissions = RxPermissions(activity())

    private val bluetoothPermission = Manifest.permission.BLUETOOTH_ADMIN


    override fun hasBluetoothPermission(): Boolean {
         return PermissionChecker.checkSelfPermission(context(), bluetoothPermission) == PermissionChecker.PERMISSION_GRANTED
    }

    override fun requestBluetoothPermission(): Observable<Boolean> {
        return rxPermissions.request(bluetoothPermission).onErrorReturnItem(false)
    }

    override fun hasLocationPermission(): Boolean {
        return PermissionChecker.checkSelfPermission(context(), Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED
    }

    override fun requestStartingPermissions(): Observable<Boolean> {
        return rxPermissions.request(bluetoothPermission,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION).onErrorReturnItem(false)
    }
}