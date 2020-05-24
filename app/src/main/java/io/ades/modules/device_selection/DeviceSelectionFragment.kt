package io.ades.modules.device_selection

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import io.ades.R
import io.ades.modules.device_selection.adapter.DevicesAdapter
import kotlinx.android.synthetic.main.device_selection_fragment.*

class DeviceSelectionFragment : Fragment() {

    companion object {
        fun newInstance() =
            DeviceSelectionFragment()
    }

    private lateinit var viewModel: DeviceSelectionViewModel
    private lateinit var adapter : DevicesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.device_selection_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DeviceSelectionViewModel::class.java)

        setupDevicesList()
        viewModel.devices.observe(this, Observer {
            adapter.addDevice(it)

        })

        viewModel.connectionSuccess.observe(this, Observer {
            view?.findNavController()?.navigate(R.id.action_deviceSelectionFragment_to_engineControllFragment)
        })
    }

    private fun setupDevicesList(){
        adapter = DevicesAdapter { device ->
            viewModel.connect(device)
        }

        devices_list.apply {
            adapter = this@DeviceSelectionFragment.adapter
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }
}
