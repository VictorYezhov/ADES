package io.ades.modules.satistics

import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
import io.ades.inject
import io.ades.service.device.DeviceManager
import io.reactivex.disposables.CompositeDisposable
import kotlin.random.Random
import kotlin.random.nextInt

class EngineControlViewModel : ViewModel() {

    val RPMdata = MutableLiveData<Entry>()
    val ThrottleData = MutableLiveData<Entry>()

    private val disposable = CompositeDisposable()
    private val deviceManager : DeviceManager by inject()
    var inspectionThread : Thread? = null

    fun startInspection(rpmReading : (entry: Entry) -> Unit, throttleReading  : (entry: Entry) -> Unit){
        inspectionThread?.interrupt()

        val action = Runnable {

            while (true){
                deviceManager.getRPMCarState()
            }
        }
        inspectionThread = Thread(action)
        inspectionThread?.start()
        disposable.add(deviceManager.getStateReading().subscribe {
            rpmReading(Entry(it.rpm.data.toFloat(), it.rpm.time.toFloat()))
            throttleReading(Entry(it.throttle.data, it.throttle.time.toFloat()))
        })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
        inspectionThread?.interrupt()
    }
}
