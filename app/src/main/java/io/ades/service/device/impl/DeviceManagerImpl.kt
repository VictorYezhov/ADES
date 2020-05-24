package io.ades.service.device.impl

import android.bluetooth.BluetoothSocket
import com.github.pires.obd.commands.engine.RPMCommand
import com.github.pires.obd.commands.engine.ThrottlePositionCommand
import io.ades.data.DataReading
import io.ades.data.RPMDataReading
import io.ades.inject
import io.ades.service.bluetooth.BluetoothService
import io.ades.service.device.DeviceManager
import io.reactivex.Observable
import io.reactivex.subjects.ReplaySubject


class DeviceManagerImpl : DeviceManager {

    val singleRPMReadings = ReplaySubject.create<DataReading<Int>>()
    val singleThrottleReadings = ReplaySubject.create<DataReading<Float>>()
    val rpmCarStateReadings = ReplaySubject.create<RPMDataReading>()

    private val bluetoothService : BluetoothService by inject()

    private lateinit var socket : BluetoothSocket

    override fun setTarget(socket: BluetoothSocket) {
       this.socket = socket
    }

    override fun getPRM() {
        Runnable {
            val  command=  RPMCommand()
            command.run(socket.inputStream, socket.outputStream)
            singleRPMReadings.onNext(DataReading(command.rpm, command.end))
        }.run()
    }

    override fun getThrottlePos() {
        Runnable {
            val  command= ThrottlePositionCommand()
            command.run(socket.inputStream, socket.outputStream)
            singleThrottleReadings.onNext(DataReading(command.percentage, command.end))
        }.run()
    }

    override fun getRPMCarState() {
        Runnable {
            val  commandRPM =  RPMCommand()
            commandRPM.run(socket.inputStream, socket.outputStream)

            val  commandThrottle = ThrottlePositionCommand()
            commandThrottle.run(socket.inputStream, socket.outputStream)

            rpmCarStateReadings.onNext(RPMDataReading(DataReading(commandRPM.rpm, commandRPM.end), DataReading(commandThrottle.percentage, commandThrottle.end)))
        }.run()
    }

    override fun getStateReading(): Observable<RPMDataReading> {
        return rpmCarStateReadings
    }
}