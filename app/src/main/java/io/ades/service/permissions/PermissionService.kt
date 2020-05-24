package io.ades.service.bluetooth

import io.reactivex.Observable

interface PermissionService {

    fun hasLocationPermission() : Boolean
    fun requestStartingPermissions() :Observable<Boolean>
    fun hasBluetoothPermission(): Boolean
    fun requestBluetoothPermission() : Observable<Boolean>

}