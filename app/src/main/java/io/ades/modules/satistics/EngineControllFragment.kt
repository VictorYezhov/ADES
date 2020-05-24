package io.ades.modules.satistics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import io.ades.R
import kotlinx.android.synthetic.main.engine_controll_fragment.*
import java.util.*
import kotlin.collections.ArrayList

class EngineControllFragment : Fragment(R.layout.engine_controll_fragment) {

    companion object {
        fun newInstance() = EngineControllFragment()
    }

    private lateinit var viewModel: EngineControlViewModel

    private val rmp = ArrayList<Entry>()
    private val throttle = ArrayList<Entry>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EngineControlViewModel::class.java)
        viewModel.startInspection({
            activity?.runOnUiThread {
                rmp.add(it)
                Log.i("ENTRY", "x: ${it.x}  y: ${it.y}")
                setRPMChartData()
            }
        },{
            activity?.runOnUiThread {
                throttle.add(it)
                setThrottleChartData()
            }
        })


        viewModel.RPMdata.observe(this, Observer {
            activity?.runOnUiThread {
                rmp.add(it)
                Log.i("ENTRY", "x: ${it.x}  y: ${it.y}")
                setRPMChartData()
            }

        })
        viewModel.ThrottleData.observe(this, Observer {
            throttle.add(it)
            setThrottleChartData()
        })
    }

    private fun setRPMChartData(){


        val rpmData = LineDataSet(rmp, "RPM")

        val data = LineData(rpmData)
        val left: YAxis = chart_rpm.axisLeft

        left.setDrawZeroLine(true) // draw a zero line
        chart_rpm.isHorizontalScrollBarEnabled = true
        chart_rpm.data = data
        chart_rpm.invalidate()
    }


    private fun setThrottleChartData(){


        val rpmData = LineDataSet(throttle, "Throttle")

        val data = LineData(rpmData)
        val left: YAxis = chart_throttle.axisLeft

        left.setDrawZeroLine(true) // draw a zero line

        chart_throttle.data = data
        chart_throttle.invalidate()
    }
}
