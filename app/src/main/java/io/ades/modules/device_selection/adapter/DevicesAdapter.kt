package io.ades.modules.device_selection.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.polidea.rxandroidble2.RxBleDevice
import io.ades.R


/**
 * Created by Victor on 13.02.2019.
 */
class DevicesAdapter(private val onConnectClick : (device : RxBleDevice) -> Unit) :
    RecyclerView.Adapter<DevicesAdapter.MyViewHolder>() {


    val devices = ArrayList<RxBleDevice>()

    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var code: TextView
        var connectButton: TextView

        init {
            name = itemView.findViewById(R.id.name_field)
            code = itemView.findViewById(R.id.code_field)
            connectButton = itemView.findViewById(R.id.connect_button)
        }
    }

    fun addDevice(device : RxBleDevice){
        devices.add(device)
        notifyItemInserted(devices.size - 1)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.device_item_layout,
            parent, false
        )
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val device= devices[position]
        Log.i(TAG, "onBindViewHolder, device:" + device.name)
        var providedName= device.getName()
        val providedCode = device.macAddress
        if (providedName == null) {
            holder.name.text = "Name not provided"
            providedName = "null"
        } else {
            holder.name.text = providedName
        }
        if (providedCode == null) {
            holder.code.text = "Key Not Provided"
        } else {
            holder.code.text = providedCode.replace(providedName, "")
        }
        holder.itemView.setOnClickListener { view: View? ->
            onConnectClick(device)
        }
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    companion object {
        private const val TAG = "DevicesAdapter"
    }

}
