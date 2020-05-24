package io.ades.config


import io.ades.service.bluetooth.BluetoothService

import io.ades.service.bluetooth.PermissionService
import io.ades.service.bluetooth.impl.BluetoothServiceImpl
import io.ades.service.device.DeviceManager
import io.ades.service.device.impl.DeviceManagerImpl
import io.ades.service.permissions.impl.PermissionServiceImpl
import io.ades.service.time.TimeService
import io.ades.service.time.TimeServiceImpl

import org.koin.dsl.bind
import org.koin.dsl.module

object Modules {
    val module = module {
        single { PermissionServiceImpl() } bind  PermissionService::class
        single { BluetoothServiceImpl(get()) } bind BluetoothService::class
        single { DeviceManagerImpl() } bind DeviceManager::class
        single { TimeServiceImpl() } bind TimeService::class
    }
}
